package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRq;
import roomescape.dto.ReservationRs;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 예약 조회
    @GetMapping
    public ResponseEntity<List<ReservationRs>> getReservations() {
        List<ReservationRs> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok().body(reservations);
    }

    // 예약 추가
    @PostMapping
    public ResponseEntity<ReservationRs> addReservation(@RequestBody ReservationRq reservationRq) {
        ReservationRs reservationRs = reservationService.addReservation(reservationRq);
        return ResponseEntity.ok().body(reservationRs);
    }

    // 예약 취소
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

}
