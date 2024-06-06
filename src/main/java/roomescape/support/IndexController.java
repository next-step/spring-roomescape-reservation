package roomescape.support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "/admin/index";
    }

    @GetMapping("/reservation")
    public String adminReservationPage() {
        return "/admin/reservation";
    }

    @GetMapping("/time")
    public String adminTimePage() {
        return "/admin/time";
    }

}
