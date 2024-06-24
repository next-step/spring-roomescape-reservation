package roomescape.domain.reservationtime.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.model.ReservationTimeId;
import roomescape.domain.reservationtime.service.ReservationTimeAppendRequest;
import roomescape.domain.reservationtime.service.ReservationTimeCommandService;
import roomescape.domain.reservationtime.service.ReservationTimeQueryService;

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
            @PathVariable Long timeId
    ) {
        timeCommandService.delete(new ReservationTimeId(timeId));
        return ResponseEntity.ok().build();
    }
}
