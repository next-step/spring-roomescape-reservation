package roomescape.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@ResponseBody
public class ReservationController {

    private AtomicLong atomicLong = new AtomicLong();
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/find/reservation/list")
    public ResponseEntity<List<Reservation>> list() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/create/reservation")
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto dto) {
        Reservation reservation = Reservation.toEntity(atomicLong.incrementAndGet(), dto);
        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservation/" + reservation.getId())).build();
    }

    @DeleteMapping("/delete/reservation/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Reservation deletedReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(deletedReservation);
        return ResponseEntity.noContent().build();
    }

    @PostConstruct
    public void init() {
        reservations.add(new Reservation(atomicLong.incrementAndGet(), "brown", LocalDate.parse("2020-01-01"), LocalTime.parse("10:00")));
        reservations.add(new Reservation(atomicLong.incrementAndGet(), "brown", LocalDate.parse("2020-01-02"), LocalTime.parse("11:00")));
    }
}
