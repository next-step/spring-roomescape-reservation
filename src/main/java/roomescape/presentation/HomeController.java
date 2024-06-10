package roomescape.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final String NAMESPACE = "/admin";

    @GetMapping("/")
    public String home() {
        return NAMESPACE + "/index.html";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return NAMESPACE + "/reservation.html";
    }

    @GetMapping("/time")
    public String time() {
        return NAMESPACE + "/time.html";
    }
}
