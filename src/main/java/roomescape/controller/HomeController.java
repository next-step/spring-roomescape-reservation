package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/admin/reservation")
    public String adminReservation(Model model) {
        return "admin/reservation-legacy";
    }
}
