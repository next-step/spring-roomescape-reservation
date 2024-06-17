package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeAdminService;

import java.util.List;

@Controller
@RequestMapping("/times")
public class ReservationTimeAdminController {
    private final ReservationTimeAdminService reservationTimeAdminService;

    public ReservationTimeAdminController(ReservationTimeAdminService reservationTimeAdminService) {
        this.reservationTimeAdminService = reservationTimeAdminService;
    }

    @GetMapping("/admin/time")
    public String getReservationTimePage() {
        return "admin/time";
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
