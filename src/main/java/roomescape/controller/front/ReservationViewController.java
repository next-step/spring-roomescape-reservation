package roomescape.controller.front;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationViewController {

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "admin/reservation";
    }

    @GetMapping("/time")
    public String time() {
        return "admin/time";
    }
}