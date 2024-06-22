package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public int insert(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @GetMapping
    public List<Reservation> read() {
        return reservationService.lookUpReservation();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }
}
