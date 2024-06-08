package roomescape.application.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.api.dto.response.FindReservationsResponse;
import roomescape.application.service.ReservationService;
import roomescape.domain.reservation.Reservations;

import java.util.List;

@RestController
public class ReservationQueryApi {

    private final ReservationService reservationService;

    @Autowired
    public ReservationQueryApi(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<FindReservationsResponse>> findReservations() {
        Reservations reservations = reservationService.findAllReservations();

        return ResponseEntity.ok(FindReservationsResponse.toFindReservationsResponses(reservations));
    }
}
