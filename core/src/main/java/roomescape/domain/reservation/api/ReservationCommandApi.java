package roomescape.domain.reservation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.application.ReservationCommandService;
import roomescape.domain.reservation.application.ReservationQueryService;
import roomescape.domain.reservation.application.request.ReserveRequest;
import roomescape.domain.reservation.domain.ReservationId;
import roomescape.global.rest.ApiResponse;

@RestController
@RequiredArgsConstructor
public class ReservationCommandApi {

    private final ReservationCommandService commandService;
    private final ReservationQueryService queryService;

    @PostMapping("/reservations")
    public ApiResponse<Object> reserve(
            @RequestBody ReserveRequest request
    ) {
        request.validateAllFieldsExist();
        final ReservationId reservationId = commandService.reserve(request);
        return ApiResponse.ok(new ReservationCreateHttpResponse(reservationId.value()));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ApiResponse<Object> cancel(
            @PathVariable(name = "reservationId") Long reservationId
    ) {
        commandService.cancel(new ReservationId(reservationId));
        return ApiResponse.okWithEmptyData();
    }
}
