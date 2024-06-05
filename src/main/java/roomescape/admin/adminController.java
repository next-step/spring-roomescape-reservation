package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminController {

    @GetMapping("/")
    String index() {
        return "admin/index";
    }
}
