package nextstep.reservation.web;

import java.util.List;
import nextstep.reservation.domain.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @PostMapping
    ResponseEntity<Void> makeReservation() {
        return null;
    }

    @GetMapping
    ResponseEntity<List<Reservation>> listReservations() {
        return null;
    }

    @DeleteMapping
    ResponseEntity<Void> cancelReservation() {
        return null;
    }
}
