package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ReservationAdminController {

    private final ReservationAdminService reservationAdminService;

    public ReservationAdminController(ReservationAdminService reservationAdminService) {
        this.reservationAdminService = reservationAdminService;
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {

        Reservation newReservation = reservationAdminService.createReservation(reservation);

        return ResponseEntity.ok().body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservation() {
        List<Reservation> reservations = reservationAdminService.getReservations();
        return ResponseEntity.ok().body(reservations);
    }

/*
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        Reservation removingReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(removingReservation);

        return ResponseEntity.ok().build();
    }*/
}
