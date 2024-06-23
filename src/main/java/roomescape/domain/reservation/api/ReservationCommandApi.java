package roomescape.domain.reservation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.api.response.ReserveHttpResponse;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.service.ReservationCommandService;
import roomescape.domain.reservation.service.request.ReserveRequest;
import roomescape.domain.reservation.service.response.ReserveResponse;

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
