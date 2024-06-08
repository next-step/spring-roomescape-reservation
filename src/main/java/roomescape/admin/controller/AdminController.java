package roomescape.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private static final String BASE_PATH = "/admin";

    @GetMapping("/")
    public String init(){
        return BASE_PATH+"/index.html";
    }

}