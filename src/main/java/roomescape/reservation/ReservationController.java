package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(@Autowired ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> reservations() {
        return reservationService.reservations().toList();
    }

    @PostMapping
    public Reservation add(@RequestBody Reservation newReservation) {
        return reservationService.add(newReservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationService.delete(id);
    }
}

