package roomescape.reservation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import roomescape.entities.Reservation;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/all")
    public List<Reservation> searchReservations(){
        return reservationService.searchAllReservations();
    }

    @PostMapping("/add")
    public void addReservation(@RequestBody Reservation reservation) {
        reservationService.addReservation(reservation);
    }
}
