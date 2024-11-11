package roomescape.domain.reservationtime.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservationtime.application.ReservationTimeAppendRequest;
import roomescape.domain.reservationtime.application.ReservationTimeCommandService;
import roomescape.domain.reservationtime.application.ReservationTimeQueryService;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.global.rest.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationTimeApi {

    private final ReservationTimeCommandService timeCommandService;
    private final ReservationTimeQueryService timeQueryService;

    @PostMapping("/times")
    public ApiResponse<ReservationTimeAppendHttpResponse> append(
            @RequestBody ReservationTimeAppendRequest request
    ) {
        final ReservationTime appended = timeCommandService.append(request);
        return ApiResponse.ok(ReservationTimeAppendHttpResponse.from(appended));
    }

    @GetMapping("/times")
    public ApiResponse<List<ReservationTimeAppendHttpResponse>> fetchAll() {
        List<ReservationTime> times = timeQueryService.fetchAll();
        return ApiResponse.ok(ReservationTimeAppendHttpResponse.from(times));
    }

    @DeleteMapping("/times/{timeId}")
    public ApiResponse<Object> delete(
            @PathVariable(name = "timeId") Long timeId
    ) {
        timeCommandService.delete(new ReservationTimeId(timeId));
        return ApiResponse.okWithEmptyData();
    }
}
