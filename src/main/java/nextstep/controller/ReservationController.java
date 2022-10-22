package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationRequest;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    ResponseEntity<String> createReservations(@RequestBody ReservationRequest reservationRequest) throws URISyntaxException {
        long id = reservationService.create(reservationRequest.getDate(), reservationRequest.getTime(), reservationRequest.getName());
        return ResponseEntity.created(new URI("/reservations/" + id)).build();
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
