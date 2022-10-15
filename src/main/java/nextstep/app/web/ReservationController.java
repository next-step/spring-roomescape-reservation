package nextstep.app.web;

import nextstep.app.web.dto.ReservationCreateRequest;
import nextstep.app.web.dto.ReservationResponse;
import nextstep.core.Reservation;
import nextstep.core.ReservationRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RequestMapping(value = "/reservations", consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ReservationController {
    private final ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = repository.save(request.to());
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> list(@RequestParam String date) {
        List<Reservation> reservations = repository.findAllByDate(LocalDate.parse(date));
        return ResponseEntity.ok(
                reservations.stream()
                        .map(ReservationResponse::from)
                        .toList()
        );
    }
}
