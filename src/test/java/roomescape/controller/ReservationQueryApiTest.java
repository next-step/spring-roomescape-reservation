package roomescape.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.application.api.ReservationQueryApi;
import roomescape.application.api.dto.response.FindReservationsResponse;
import roomescape.application.service.ReservationService;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.Reservations;
import roomescape.domain.reservation.vo.ReservationDateTime;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationQueryApi.class)
class ReservationQueryApiTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    private Reservations reservations;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        Reservation reservation = new Reservation(
                new ReservationId(1L),
                new ReservationName("kilian"),
                new ReservationDateTime(LocalDateTime.of(2024, 6, 6, 19, 25))
        );

        reservations = new Reservations(List.of(reservation));
    }

    @Test
    void findReservations() throws Exception {
        given(reservationService.findAllReservations()).willReturn(reservations);
        List<FindReservationsResponse> response =
                FindReservationsResponse.toFindReservationsResponses(reservations);

        mockMvc.perform(
                        get("/reservations")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

}