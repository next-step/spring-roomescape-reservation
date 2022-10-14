package nextstep.reservation;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.reservation.web.ReservationController;
import nextstep.reservation.web.request.MakeReservationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationController reservationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController)
            .build();
    }

    @Test
    void makeReservation() throws Exception {
        String requestBody = objectMapper.writeValueAsString(
            new MakeReservationRequest(
                "2022-08-11", "13:00", "name"
            )
        );

        mockMvc.perform(
                post("/reservations").content(requestBody)
            )
            .andExpect(status().isCreated())
            // TODO ggyool 추후 id 받아서 추가해야함
            .andExpect(header().string("Location", startsWith("/reservations/")));
    }

    @Test
    void listReservations() throws Exception {
        mockMvc.perform(
                get("/reservations").param("date", "2022-08-11")
            )
            .andExpect(status().isOk());
    }

    @Test
    void cancelReservation() throws Exception {
        mockMvc.perform(
                get("/reservations")
                    .param("date", "2022-08-11")
                    .param("time", "`13:00`")
            )
            .andExpect(status().isNoContent());
    }
}
