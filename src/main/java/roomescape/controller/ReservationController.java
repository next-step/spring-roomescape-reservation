package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @PostMapping
    public Reservation create(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index);
        reservations.add(newReservation);
        return newReservation;
    }

    @GetMapping
    public List<Reservation> read() {
        return reservations;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(findReservation -> Objects.equals(findReservation.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);
    }
}
