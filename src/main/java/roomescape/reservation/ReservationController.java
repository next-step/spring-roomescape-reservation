package roomescape.reservation;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> reservations() {
        return reservationService.reservations();
    }

    @PostMapping
    public Reservation add(@Valid @RequestBody NewReservation newReservation) {
        return reservationService.add(newReservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationService.delete(id);
    }
}

