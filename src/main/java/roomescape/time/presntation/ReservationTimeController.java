package roomescape.time.presntation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import roomescape.time.application.ReservationTimeService;
import roomescape.time.domain.ReservationTime;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTime reservationTime) {
        ReservationTime savedReservationTime = reservationTimeService.createReservationTime(reservationTime);
        return ResponseEntity.ok(savedReservationTime);
    }

    @GetMapping("times")
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.getReservationTimes();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/times/{reservationTimeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long reservationTimeId) {
        reservationTimeService.deleteReservationTime(reservationTimeId);
        return ResponseEntity.ok().build();
    }
}
