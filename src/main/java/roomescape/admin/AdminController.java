package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	@GetMapping("/")
	public String index() {
		return "admin/index";
	}

	@GetMapping("/admin/reservation")
	public String home() {
		return "admin/reservation-legacy.html";
	}
}
