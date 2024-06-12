package roomescape.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/reservation")
    public String manageReservation() {
        return "admin/reservation-new";
    }

    @GetMapping("/admin/time")
    public String manageReservationTime() {
        return "admin/time";
    }

    @GetMapping("/admin/theme")
    public String manageTheme() {
        return "admin/theme";
    }
}
