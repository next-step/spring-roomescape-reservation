package nextstep.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.persistence.ReservationStorage;
import nextstep.reservation.web.request.MakeReservationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationStorage reservationStorage;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @BeforeEach
    void initializeData() {
        reservationStorage.insert(
            new Reservation(LocalDate.parse("2022-08-11"), LocalTime.parse("12:00"), "ggyool")
        );
    }

    @Test
    void makeReservation() throws Exception {
        String requestBody = objectMapper.writeValueAsString(
            new MakeReservationRequest(
                "2022-08-11", "13:00", "name"
            )
        );

        mockMvc.perform(
                post("/reservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", matchesRegex("/reservations/\\d+")));

        assertReservationCount("2022-08-11", 2);
    }

    @Test
    void makeDuplicatedReservation() throws Exception {
        String requestBody = objectMapper.writeValueAsString(
            new MakeReservationRequest(
                "2022-08-11", "12:00", "name"
            )
        );

        mockMvc.perform(
                post("/reservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isConflict())
            .andExpect(content().string("해당 시간에 이미 예약이 존재합니다. [2022-08-11 12:00]"));

        assertReservationCount("2022-08-11", 1);
    }

    @Test
    void listReservations() throws Exception {
        mockMvc.perform(
                get("/reservations").param("date", "2022-08-11")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].date").value("2022-08-11"))
            .andExpect(jsonPath("$[0].time").value("12:00:00"))
            .andExpect(jsonPath("$[0].name").value("ggyool"));
    }

    @Test
    void cancelReservation() throws Exception {
        mockMvc.perform(
                delete("/reservations")
                    .param("date", "2022-08-11")
                    .param("time", "12:00")
            )
            .andExpect(status().isNoContent());

        assertReservationCount("2022-08-11", 0);
    }

    private void assertReservationCount(String date, int count) {
        List<Reservation> reservations = reservationStorage.findByDate(LocalDate.parse(date));
        assertThat(reservations).hasSize(count);
    }
}
