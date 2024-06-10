package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation-new")
    public String reservation() {
        return "admin/reservation-new";
    }

    @GetMapping("/admin/time")
    public String time() {
        return "admin/time";
    }

    @GetMapping("/admin/theme")
    public String theme() {
        return "admin/theme";
    }
}
