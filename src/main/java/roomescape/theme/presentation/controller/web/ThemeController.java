package roomescape.theme.presentation.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThemeController {

    @GetMapping("/admin/theme")
    public String theme() {
        return "admin/theme";
    }
}
