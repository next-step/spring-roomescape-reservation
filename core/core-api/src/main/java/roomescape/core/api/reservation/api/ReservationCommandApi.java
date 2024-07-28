package roomescape.core.api.reservation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.core.api.reservation.api.response.ReserveHttpResponse;
import roomescape.core.api.reservation.api.response.ReserveResponse;
import roomescape.core.api.reservation.application.ReservationCommandService;
import roomescape.core.api.reservation.application.request.ReserveRequest;
import roomescape.core.domain.reservation.ReservationId;

@RestController
@RequiredArgsConstructor
public class ReservationCommandApi {

    private final ReservationCommandService commandService;

    @PostMapping("/reservations")
    public ResponseEntity<ReserveHttpResponse> reserve(
            @RequestBody ReserveRequest request
    ) {
        request.validateAllFieldsExist();
        final ReserveResponse response = commandService.reserve(request);
        return ResponseEntity.ok().body(ReserveHttpResponse.from(response));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> cancel(
            @PathVariable(name = "reservationId") Long reservationId
    ) {
        commandService.cancel(new ReservationId(reservationId));
        return ResponseEntity.ok().build();
    }
}
