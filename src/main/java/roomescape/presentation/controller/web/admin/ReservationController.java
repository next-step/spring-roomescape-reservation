package roomescape.presentation.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReservationController {

    @RequestMapping("/admin/reservation")
    public String reservationLegacy() {
        return "admin/reservation";
    }
}
