package nextstep.api;

import nextstep.domain.Reservations;
import nextstep.api.dto.ReservationCreateRequest;
import nextstep.api.dto.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReservationController {
    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest request) {
        Long id = Reservations.add(request.toEntity());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> findAllReservationsByDate(@RequestParam String date) {
        List<ReservationResponse> reservations = Reservations.findAllByDate(date).stream()
            .map(ReservationResponse::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<Void> removeReservation(@RequestParam String date, @RequestParam String time) {
        Reservations.removeBySchedule(date, time);
        return ResponseEntity.noContent().build();
    }
}
