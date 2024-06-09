package roomescape.presentation;

import static roomescape.application.ReservationTimeServiceInput.createReservationTimeServiceInput;
import static roomescape.presentation.dto.ReservationTimeResponse.createReservationTimeResponse;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.ReservationTimeService;
import roomescape.application.ReservationTimeServiceInput;
import roomescape.application.ReservationTimeServiceOutput;
import roomescape.presentation.dto.ReservationResponse;
import roomescape.presentation.dto.ReservationTimeRequest;
import roomescape.presentation.dto.ReservationTimeResponse;

@RestController
public class ReservationTimeController {

    private static final String NAMESPACE = "/times";

    private final ReservationTimeService reservationTimeService;

    private ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping(NAMESPACE)
    public ResponseEntity<ReservationTimeResponse> createReservationTime(
        @RequestBody ReservationTimeRequest request) {
        ReservationTimeServiceInput input = createReservationTimeServiceInput(request);
        return ResponseEntity
            .ok()
            .body(createReservationTimeResponse(reservationTimeService.create(input)));
    }

    @GetMapping(NAMESPACE)
    public ResponseEntity<List<ReservationTimeResponse>> retrieveReservationTimes() {
        List<ReservationTimeServiceOutput> outputs = reservationTimeService.retrieveReservationTimes();
        List<ReservationTimeResponse> responses = outputs.stream()
            .map(ReservationTimeResponse::createReservationTimeResponse)
            .toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping(NAMESPACE + "/{id}")
    public ResponseEntity<ReservationResponse> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
