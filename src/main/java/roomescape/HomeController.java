package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/admin/reservation")
    public String adminReservation(Model model) {
        return "admin/reservation";
    }

    @GetMapping("/admin/time")
    public String time(Model model) {
        return "admin/time";
    }

    @GetMapping("/admin/theme")
    public String theme(Model model) {
        return "admin/theme";
    }

    @GetMapping("/")
    public String home(Model model) {
        return "admin/index";
    }
}
