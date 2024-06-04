package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        List<ReservationDto> reservationDtos = reservations.stream().map(Reservation::toDto).toList();
        return ResponseEntity.ok().body(reservationDtos);

    }

}
