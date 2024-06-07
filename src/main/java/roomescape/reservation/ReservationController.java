package roomescape.reservation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import roomescape.entities.Reservation;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> searchReservations(){
        return reservationService.searchAllReservations();
    }

    @PostMapping
    public void addReservation(@RequestBody Reservation reservation) {
        reservationService.addReservation(reservation);
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }
}
