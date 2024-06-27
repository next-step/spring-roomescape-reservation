package roomescape.application.presentation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.presentation.api.dto.response.FindAllReservationsResponse;
import roomescape.application.service.ReservationQueryService;
import roomescape.domain.reservation.ReservationViews;

import java.util.List;

@RestController
public class ReservationQueryApi {

    private final ReservationQueryService reservationQueryService;

    public ReservationQueryApi(ReservationQueryService reservationQueryService) {
        this.reservationQueryService = reservationQueryService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<FindAllReservationsResponse>> findAll() {
        ReservationViews reservationViews = reservationQueryService.findAll();

        return ResponseEntity.ok(FindAllReservationsResponse.toFindAllReservationsResponses(reservationViews));
    }
}
