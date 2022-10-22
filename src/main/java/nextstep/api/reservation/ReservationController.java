package nextstep.api.reservation;

import nextstep.api.reservation.dto.ReservationCreateRequest;
import nextstep.api.reservation.dto.ReservationResponse;
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
    public ResponseEntity<List<ReservationResponse>> findAllReservationsByDate(@RequestParam String date) {
        List<ReservationResponse> reservations = service.findAllByDate(date).stream()
            .map(ReservationResponse::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<Void> removeReservation(@RequestParam String date, @RequestParam String time) {
        service.removeByDateAndTime(date, time);
        return ResponseEntity.noContent().build();
    }
}
