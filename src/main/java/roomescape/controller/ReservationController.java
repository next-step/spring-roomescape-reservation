package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {

    private AtomicLong index = new AtomicLong();
    private List<Reservation> reservations = List.of(
            new Reservation(index.incrementAndGet(), "브라운", "2023-08-05", "15:40"),
            new Reservation(index.incrementAndGet(), "코니", "2023-08-05", "15:40"));

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getReservations() {

        final List<ReservationDto> reservationDtos = reservations.stream().map(Reservation::toDto).toList();
        return ResponseEntity.ok().body(reservationDtos);

    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        final Reservation reservation = reservationDto.toEntity();
        reservations = new ArrayList<>(reservations);
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation.toDto());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        reservations = reservations.stream()
                .filter(reservation -> !reservation.getId().equals(id))
                .toList();
        return ResponseEntity.ok().build();
    }
}
