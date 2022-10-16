package nextstep.api;

import nextstep.domain.Reservation;
import nextstep.domain.Reservations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class ReservationController {
    private static final String RESERVATION_PATH = "/reservations";

    private final Reservations reservations;

    public ReservationController() {
        this.reservations = new Reservations();
    }

    @PostMapping(RESERVATION_PATH)
    public ResponseEntity<Void> create(@RequestBody ReservationCreationRequest request) {
        Reservation reservation = new Reservation(request.getDate(), request.getTime(), request.getName());
        reservations.add(reservation);
        return ResponseEntity.created(URI.create(RESERVATION_PATH)).build();
    }

    @GetMapping(RESERVATION_PATH)
    public ResponseEntity<List<Reservation>> read(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(reservations.findBy(date));
    }

    @DeleteMapping(RESERVATION_PATH)
    public ResponseEntity<Void> delete(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {
        reservations.remove(date, time);
        return ResponseEntity.noContent().build();
    }
}
