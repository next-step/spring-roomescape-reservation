package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimeAdminController {

    @GetMapping("/admin/time")
    public String getReservationPage() {
        return "admin/time";
    }

}
