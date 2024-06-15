package roomescape.application.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.application.api.dto.request.CreateReservationRequest;
import roomescape.application.api.dto.response.CreateReservationResponse;
import roomescape.application.service.ReservationCommandService;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationCommandApi.class)
class ReservationCommandApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @MockBean
    private ReservationCommandService reservationCommandService;
    @Autowired
    private MockMvc mockMvc;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation(
                new ReservationId(1L),
                new ReservationName("kilian"),
                new ReservationDate(LocalDate.of(2024, 6, 6)),
                new ReservationTimeId(1L)
        );
    }

    @Test
    @DisplayName("예약 생성 API 컨트롤러 테스트")
    void createReservationTest() throws Exception {
        String reservationName = "kilian";
        CreateReservationRequest createReservationRequest =
                new CreateReservationRequest("2024-06-06", reservationName, 1L);

        given(reservationCommandService.createReservation(any()))
                .willReturn(reservation);

        CreateReservationResponse expectedResponse = CreateReservationResponse.from(reservation);

        mockMvc.perform(
                        post("/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createReservationRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @DisplayName("예약 삭제 API 컨트롤러 테스트")
    void deleteReservationTest() throws Exception {
        Long reservationId = 1L;

        doNothing().when(reservationCommandService).deleteReservation(any());

        mockMvc.perform(
                        delete("/reservations/{reservationId}", reservationId)
                )
                .andExpect(status().isOk());
    }
}
