package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeAdminService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeAdminApiController {
    private final ReservationTimeAdminService reservationTimeAdminService;

    public ReservationTimeAdminApiController(ReservationTimeAdminService reservationTimeAdminService) {
        this.reservationTimeAdminService = reservationTimeAdminService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTime time) {
        ReservationTime reservationTime = reservationTimeAdminService.createReservationTime(time);

        return ResponseEntity.ok().body(reservationTime);

    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getReservationTime() {
        List<ReservationTime> reservationTimes = reservationTimeAdminService.getReservationTime();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeAdminService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
