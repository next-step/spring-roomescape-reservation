package roomescape.core.api.reservation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.core.api.reservation.api.response.ReserveHttpResponse;
import roomescape.core.api.reservation.application.ReservationCommandService;
import roomescape.core.api.reservation.application.ReservationQueryService;
import roomescape.core.api.reservation.application.dto.ReservationTimeThemeDto;
import roomescape.core.api.reservation.application.request.ReserveRequest;
import roomescape.core.domain.reservation.ReservationId;

@RestController
@RequiredArgsConstructor
public class ReservationCommandApi {

    private final ReservationCommandService commandService;
    private final ReservationQueryService queryService;

    @PostMapping("/reservations")
    public ResponseEntity<ReserveHttpResponse> reserve(
            @RequestBody ReserveRequest request
    ) {
        request.validateAllFieldsExist();
        final ReservationId reservationId = commandService.reserve(request);

        final ReservationTimeThemeDto dto = queryService.fetchReservationTimeThemeBy(reservationId);

        return ResponseEntity.ok().body(ReserveHttpResponse.from(dto));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> cancel(
            @PathVariable(name = "reservationId") Long reservationId
    ) {
        commandService.cancel(new ReservationId(reservationId));
        return ResponseEntity.ok().build();
    }
}
