package roomescape.domain.reservationtime.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.service.ReservationTimeAppendRequest;
import roomescape.domain.reservationtime.service.ReservationTimeCommandService;

@RestController
@RequiredArgsConstructor
public class ReservationTimeApi {

    private final ReservationTimeCommandService timeCommandService;

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeAppendHttpResponse> append(
            @RequestBody ReservationTimeAppendRequest request
    ) {
        final ReservationTime appended = timeCommandService.append(request);

        return ResponseEntity.ok()
                .body(ReservationTimeAppendHttpResponse.from(appended));
    }
}
