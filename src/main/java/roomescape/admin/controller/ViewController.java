package roomescape.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private static final String BASE_PATH = "/admin";

    @GetMapping("/")
    public String init(){
        return BASE_PATH+"/index.html";
    }

    @GetMapping("/reservation")
    public String reservation(){
        return BASE_PATH+"/reservation.html";
    }

    @GetMapping("/time")
    public String time(){
        return BASE_PATH+"/time.html";
    }
}