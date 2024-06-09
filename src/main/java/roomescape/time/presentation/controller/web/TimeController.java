package roomescape.time.presentation.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimeController {

    @GetMapping("/admin/time")
    public String time() {
        return "/admin/time";
    }
}
