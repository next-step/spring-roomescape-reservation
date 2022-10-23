package nextstep.web.reservation;

import nextstep.web.reservation.dto.ReservationCreateRequest;
import nextstep.web.reservation.dto.ReservationWebResponse;
import nextstep.domain.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReservationController {
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest request) {
        Long id = service.create(request.toEntity());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationWebResponse>> findAllReservationsByDate(@RequestParam String date) {
        List<ReservationWebResponse> responses = service.findAllByDate(date).stream()
            .map(ReservationWebResponse::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<Void> removeReservationByScheduleId(@RequestParam Long scheduleId) {
        service.removeByScheduleId(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
