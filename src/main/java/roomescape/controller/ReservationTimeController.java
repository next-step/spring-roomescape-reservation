package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private ReservationTimeService reservationTimeService;

    @Autowired
    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public Long insert(@RequestBody ReservationTime reservationTime) {
        return reservationTimeService.addReservationTime(reservationTime);
    }

    @GetMapping
    public List<ReservationTime> read() {
        return reservationTimeService.lookUpReservationTime();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
    }
}
