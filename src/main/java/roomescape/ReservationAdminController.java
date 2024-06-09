package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationAdminController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {

        Reservation newReservation = Reservation.toEntity(reservation, index.incrementAndGet());
        reservations.add(newReservation);

        return ResponseEntity.ok().body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservation() {
        return ResponseEntity.ok().body(reservations);
    }
}
