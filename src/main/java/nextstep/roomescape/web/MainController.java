package nextstep.roomescape.web;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.roomescape.core.Reservation;
import nextstep.roomescape.core.Reservations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final Reservations reservations;

    public MainController(Reservations reservations) {
        this.reservations = reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<URI> register(@RequestBody Reservation request) {
        final int registeredId = reservations.register(request.getDate(), request.getTime(), request.getName());
        return ResponseEntity.created(URI.create("/reservations/" + registeredId)).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> register(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(reservations.findAllByDate(date));
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<Void> delete(
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @DateTimeFormat(pattern = "HH:mm") LocalTime time
    ) {
        reservations.deleteByDateAndTime(date, time);
        return ResponseEntity.noContent().build();
    }
}
