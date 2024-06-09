package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeAdminService;

@Controller
public class ReservationTimeAdminController {
    private final ReservationTimeAdminService reservationTimeAdminService;

    public ReservationTimeAdminController(ReservationTimeAdminService reservationTimeAdminService) {
        this.reservationTimeAdminService = reservationTimeAdminService;
    }

    @GetMapping("/admin/time")
    public String getReservationTimePage() {
        return "admin/time";
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTime time) {
        ReservationTime reservationTime = reservationTimeAdminService.createReservationTime(time);

        return ResponseEntity.ok().body(reservationTime);

    }

}
