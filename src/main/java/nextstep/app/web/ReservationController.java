package nextstep.app.web;

import nextstep.app.web.dto.ReservationCreateRequest;
import nextstep.core.Reservation;
import nextstep.core.ReservationRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
