package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation newReservation) {
        Reservation reservation = Reservation.toEntity(newReservation, index.getAndIncrement());
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        Reservation removedReservation;

        try {
             removedReservation = reservations.stream()
                    .filter(reservation -> Objects.equals(reservation.getId(), id))
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        reservations.remove(removedReservation);
        return ResponseEntity.ok().build();
    }
}
