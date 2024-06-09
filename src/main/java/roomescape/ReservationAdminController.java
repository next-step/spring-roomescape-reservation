package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationAdminController {

    @GetMapping("/admin/reservation")
    public String getReservationPage(){
        return "admin/reservation-legacy";
    }
}
