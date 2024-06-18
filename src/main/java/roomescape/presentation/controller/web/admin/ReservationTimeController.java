package roomescape.presentation.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReservationTimeController {

    @RequestMapping("/admin/time")
    public String reservationTime() {
        return "admin/time";
    }
}
