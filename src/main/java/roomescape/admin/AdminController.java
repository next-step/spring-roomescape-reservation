package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private static final String BASE_PATH = "/admin";

    @GetMapping("/")
    public String init(){
        return BASE_PATH+"/index.html";
    }

    @GetMapping("/admin/reservation")
    public String reservation(){
        return BASE_PATH+"/reservation-legacy.html";
    }
}
