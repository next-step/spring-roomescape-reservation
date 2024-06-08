package roomescape.application.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.api.dto.request.CreateReservationRequest;
import roomescape.application.api.dto.response.CreateReservationResponse;
import roomescape.application.service.ReservationService;
import roomescape.domain.reservation.Reservation;

@RestController
public class ReservationCommandApi {
    private final ReservationService reservationService;

    @Autowired
    public ReservationCommandApi(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<CreateReservationResponse> createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        Reservation reservation = reservationService.createReservation(createReservationRequest.toCreateReservationCommand());

        return ResponseEntity.ok(CreateReservationResponse.from(reservation));
    }
}
