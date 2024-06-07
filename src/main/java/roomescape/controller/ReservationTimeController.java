package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeService.createTime(reservationTimeRequest);

        return ResponseEntity.ok(reservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readAllTime() {
        return ResponseEntity.ok(reservationTimeService.readAllTime());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long timeId) {
        reservationTimeService.deleteTime(timeId);
        return ResponseEntity.ok().build();
    }
}
