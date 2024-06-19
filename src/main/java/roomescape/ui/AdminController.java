package roomescape.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {
    @GetMapping("/reservation")
    public String readReservationAdmin() {
        return "admin/reservation-new";
    }

    @GetMapping("/time")
    public String readReservationTimeAdmin() {
        return "admin/time";
    }

    @GetMapping("/theme")
    public String readThemeAdmin() {
        return "admin/theme";
    }
}
