package roomescape.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationAdminController {

	@GetMapping("/")
	public String admin() {
		return "/admin/index";
	}

	@GetMapping("/reservation")
	public String reservation() {
		return "/admin/reservation";
	}

	@GetMapping("/time")
	public String time() {
		return "/admin/time";
	}

}
