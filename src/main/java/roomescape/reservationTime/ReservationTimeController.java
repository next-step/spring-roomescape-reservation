package roomescape.reservationTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entities.ReservationTime;
import roomescape.reservationTime.data.ReservationTimeAddRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTime addTime(@RequestBody ReservationTimeAddRequestDTO reservationTimeAddRequestDTO) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeAddRequestDTO);
        reservationTimeService.addTime(reservationTime);
        return reservationTime;
    }

    @GetMapping
    public List<ReservationTime> searchTimes() {
        return reservationTimeService.searchAllTimes();
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable("id") Long id){
        reservationTimeService.cancelReservationTime(id);
    }



}
