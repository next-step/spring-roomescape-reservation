package roomescape.reservation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import roomescape.entities.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired private final ReservationService reservationService;
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong();

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/all")
    public List<Reservation> searchReservations(){
        return this.reservations;
    }

    @PostMapping("/add")
    public void addReservation(@RequestBody Reservation reservation) {
        this.reservations.add(reservation);
    }
}
