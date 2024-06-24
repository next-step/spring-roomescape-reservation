package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeRq;
import roomescape.dto.ReservationTimeRs;
import roomescape.service.ReservationTimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    // 시간 조회
    @GetMapping
    public ResponseEntity<List<ReservationTimeRs>> getReservationTimes() {
        List<ReservationTimeRs> reservationTimes = reservationTimeService.getAllReservationTimes();
        return ResponseEntity.ok().body(reservationTimes);
    }

    // 시간 추가
    @PostMapping
    public ResponseEntity<ReservationTimeRs> addReservationTime(@RequestBody ReservationTimeRq reservationTimeRq) {
        ReservationTimeRs reservationTimeRs = reservationTimeService.addReservationTime(reservationTimeRq);
        return ResponseEntity.created(URI.create("/times/" + reservationTimeRs.getId())).body(reservationTimeRs);
    }

    // 시간 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }

}
