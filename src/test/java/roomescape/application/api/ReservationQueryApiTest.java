package roomescape.application.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.application.api.dto.response.FindAllReservationsResponse;
import roomescape.application.service.ReservationService;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.Reservations;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationQueryApi.class)
class ReservationQueryApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    private Reservations reservations;

    @BeforeEach
    void setUp() {
        Reservation reservation = new Reservation(
                new ReservationId(1L),
                new ReservationName("kilian"),
                new ReservationDate(LocalDate.of(2024, 6, 6)),
                new ReservationTimeId(1L)
        );

        reservations = new Reservations(List.of(reservation));
    }

    @Test
    @DisplayName("예약 전체 조회 API 컨트롤러 테스트")
    void findAllReservationsTest() throws Exception {
        given(reservationService.findAllReservations()).willReturn(reservations);
        List<FindAllReservationsResponse> response =
                FindAllReservationsResponse.toFindAllReservationsResponses(reservations);

        mockMvc.perform(
                        get("/reservations")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
}
