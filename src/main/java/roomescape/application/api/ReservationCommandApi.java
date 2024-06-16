package roomescape.application.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.api.dto.request.CreateReservationRequest;
import roomescape.application.api.dto.response.CreateReservationResponse;
import roomescape.application.service.ReservationCommandService;
import roomescape.application.service.command.DeleteReservationCommand;
import roomescape.domain.reservation.Reservation;

@RestController
public class ReservationCommandApi {

    private final ReservationCommandService reservationCommandService;

    public ReservationCommandApi(ReservationCommandService reservationCommandService) {
        this.reservationCommandService = reservationCommandService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<CreateReservationResponse> createReservation(@RequestBody @Valid CreateReservationRequest request) {
        Reservation reservation = reservationCommandService.createReservation(request.toCreateReservationCommand());

        return ResponseEntity.ok(CreateReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationCommandService.deleteReservation(new DeleteReservationCommand(reservationId));

        return ResponseEntity.ok().build();
    }
}
