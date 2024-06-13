package roomescape.application.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.api.dto.response.FindAllReservationsResponse;
import roomescape.application.service.ReservationService;
import roomescape.domain.reservation.Reservations;

import java.util.List;

@RestController
public class ReservationQueryApi {

    private final ReservationService reservationService;

    public ReservationQueryApi(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<FindAllReservationsResponse>> findAllReservations() {
        Reservations reservations = reservationService.findAllReservations();

        return ResponseEntity.ok(FindAllReservationsResponse.toFindAllReservationsResponses(reservations));
    }
}
