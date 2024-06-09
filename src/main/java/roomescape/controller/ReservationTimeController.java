package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    @Autowired
    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    // 시간 조회
    @GetMapping
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.getAllReservationTimes();
        return ResponseEntity.ok().body(reservationTimes);
    }

    // 시간 추가
    @PostMapping
    public ResponseEntity<ReservationTime> addReservationTime(@RequestBody ReservationTime reservationTime) {
        reservationTimeService.addReservationTime(reservationTime);
        return ResponseEntity.ok().body(reservationTime);
    }

    // 시간 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }

}
