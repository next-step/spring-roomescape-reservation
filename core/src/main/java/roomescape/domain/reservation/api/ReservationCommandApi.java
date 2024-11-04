package roomescape.domain.reservation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.api.response.ReserveHttpResponse;
import roomescape.domain.reservation.application.ReservationCommandService;
import roomescape.domain.reservation.application.ReservationQueryService;
import roomescape.domain.reservation.application.dto.ReservationTimeThemeDto;
import roomescape.domain.reservation.application.request.ReserveRequest;
import roomescape.domain.reservation.domain.ReservationId;

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
