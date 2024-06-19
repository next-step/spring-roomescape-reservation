package roomescape.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/")
    public String index() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "/admin/reservation-new";
    }

    @GetMapping("/admin/time")
    public String time() {
        return "/admin/time";
    }

    @GetMapping("/admin/theme")
    public String theme() {
        return "/admin/theme";
    }
}
