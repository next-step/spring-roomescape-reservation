package roomescape.support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservationPage() {
        return "/admin/reservation-new";
    }

    @GetMapping("/admin/time")
    public String adminTimePage() {
        return "/admin/time";
    }

    @GetMapping("/admin/theme")
    public String adminThemes() {
        return "/admin/theme";
    }
}
