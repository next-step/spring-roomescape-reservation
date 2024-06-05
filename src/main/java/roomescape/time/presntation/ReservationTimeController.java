package roomescape.time.presntation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import roomescape.time.domain.ReservationTime;
import roomescape.time.infrastructure.JdbcReservationTimeRepository;

@RestController
public class ReservationTimeController {

    private final JdbcReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(JdbcReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ResponseEntity.ok(savedReservationTime);
    }

    @GetMapping("times")
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/times/{reservationTimeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long reservationTimeId) {
        reservationTimeRepository.delete(reservationTimeId);
        return ResponseEntity.ok().build();
    }
}
