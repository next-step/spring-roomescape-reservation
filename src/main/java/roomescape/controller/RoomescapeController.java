package roomescape.controller;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@Controller
@RequestMapping("/admin")
public class RoomescapeController {

    private final ReservationTimeService reservationTimeService;

    public RoomescapeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "admin/reservation";
    }

    @GetMapping("/time")
    public String time() {
        return "admin/time";
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> times(@RequestBody ReservationTime reservationTime) {
        ReservationTime newReservationTime = reservationTimeService.create(reservationTime);
        return ResponseEntity.ok().body(newReservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> times() {
        List<ReservationTime> reservationTimes = reservationTimeService.read();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
