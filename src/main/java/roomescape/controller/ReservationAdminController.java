package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ReservationAdminController {

    @GetMapping("/time")
    public String getReservationTimePage() {
        return "admin/time";
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "admin/reservation";
    }

}
