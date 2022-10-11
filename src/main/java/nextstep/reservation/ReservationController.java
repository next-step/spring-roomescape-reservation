package nextstep.reservation;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reservation.exception.AlreadyReservedException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity reserve(@RequestBody Reservation reservation) {
        try {
            long id = reservationService.reserve(reservation);
            return ResponseEntity.created(URI.create("/reservations/" + id)).build();
        } catch (AlreadyReservedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(reservationService.getReservationList(date));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime time) {
        reservationService.removeReservation(date, time);
        return ResponseEntity.noContent().build();
    }
}
