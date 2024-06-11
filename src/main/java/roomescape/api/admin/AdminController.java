package roomescape.api.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
  @GetMapping("reservation")
  public String getReservationPage(){
    return "admin/reservation-legacy";
  }
  @GetMapping("time")
  public String getTimePage() {return "admin/time";}
}
