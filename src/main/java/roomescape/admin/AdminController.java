package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation.html";
    }

    @GetMapping("/admin/time")
    public String time() {
        return "admin/time.html";
    }

    @GetMapping("/admin/theme")
    public String theme() {
        return "admin/theme.html";
    }
}
