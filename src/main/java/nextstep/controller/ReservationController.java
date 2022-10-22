package nextstep.controller;

import nextstep.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {

    @PostMapping("/reservations")
    ResponseEntity<String> createReservations(@RequestBody Reservation reservation) {
        System.out.println(reservation);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reservations")
    ResponseEntity<Reservation> getReservations(@RequestParam String date) {
        System.out.println("date = " + date);
        return ResponseEntity.ok().body(new Reservation());
    }

    @DeleteMapping("/reservations")
    ResponseEntity<Void> deleteReservations(@RequestParam String date, @RequestParam String time) {
        System.out.println("date = " + date);
        System.out.println("time = " + time);
        return ResponseEntity.noContent().build();
    }
}
