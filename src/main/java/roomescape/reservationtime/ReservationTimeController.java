package roomescape.reservationtime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entities.ReservationTime;
import roomescape.reservationtime.data.ReservationTimeAddRequestDto;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> saveTime(@RequestBody ReservationTimeAddRequestDto reservationTimeAddRequestDto) {
        System.out.println("reservationTimeAddRequestDto = " + reservationTimeAddRequestDto);
        System.out.println("reservationTimeAddRequestDto.getTime() = " + reservationTimeAddRequestDto.getStartAt());
        ReservationTime reservationTime = reservationTimeService.saveTime(reservationTimeAddRequestDto);
        return ResponseEntity.ok().body(reservationTime);
    }

    @GetMapping
    public List<ReservationTime> findAllTimes() {
        return reservationTimeService.findAllTimes();
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable("id") Long id){
        reservationTimeService.cancelReservationTime(id);
    }
}
