package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.response.FindReservationsResponse;
import roomescape.domain.reservation.Reservations;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
public class ReservationQueryController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationQueryController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<FindReservationsResponse>> findReservations() {
        Reservations reservations = reservationService.findReservations();

        return ResponseEntity.ok(FindReservationsResponse.toFindReservationsResponses(reservations));
    }
}
