package roomescape.reservation.time;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTime add(@RequestBody ReservationTime reservationTime) {
        return reservationTimeService.add(reservationTime);
    }

    @GetMapping
    public List<ReservationTime> reservationTimes() {
        return reservationTimeService.reservationTimes();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationTimeService.delete(id);

    }

}
