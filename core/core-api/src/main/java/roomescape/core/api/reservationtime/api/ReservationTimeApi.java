package roomescape.core.api.reservationtime.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.core.api.reservationtime.application.ReservationTimeAppendRequest;
import roomescape.core.api.reservationtime.application.ReservationTimeCommandService;
import roomescape.core.api.reservationtime.application.ReservationTimeQueryService;
import roomescape.core.domain.reservationtime.ReservationTime;
import roomescape.core.domain.reservationtime.ReservationTimeId;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationTimeApi {

    private final ReservationTimeCommandService timeCommandService;
    private final ReservationTimeQueryService timeQueryService;

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeAppendHttpResponse> append(
            @RequestBody ReservationTimeAppendRequest request
    ) {
        final ReservationTime appended = timeCommandService.append(request);

        final ReservationTimeAppendHttpResponse response = ReservationTimeAppendHttpResponse.from(appended);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeAppendHttpResponse>> fetchAll() {
        List<ReservationTime> times = timeQueryService.fetchAll();

        final List<ReservationTimeAppendHttpResponse> response = ReservationTimeAppendHttpResponse.from(times);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseEntity<Void> delete(
            @PathVariable(name = "timeId") Long timeId
    ) {
        timeCommandService.delete(new ReservationTimeId(timeId));
        return ResponseEntity.ok().build();
    }
}
