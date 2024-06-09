package roomescape.application.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.api.dto.request.CreateReservationRequest;
import roomescape.application.api.dto.response.CreateReservationResponse;
import roomescape.application.service.ReservationService;
import roomescape.application.service.command.DeleteReservationCommand;
import roomescape.domain.reservation.Reservation;

@RestController
public class ReservationCommandApi {

    private final ReservationService reservationService;

    public ReservationCommandApi(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<CreateReservationResponse> createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        Reservation reservation = reservationService.createReservation(createReservationRequest.toCreateReservationCommand());

        return ResponseEntity.ok(CreateReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(new DeleteReservationCommand(reservationId));

        return ResponseEntity.ok().build();
    }
}
