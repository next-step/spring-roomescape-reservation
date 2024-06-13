package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationTimeRequest;
import roomescape.DTO.ReservationTimeResponse;
import roomescape.Service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {
    private final String PATH = "times";
    private final ReservationTimeService reservationTimeService;

    @Autowired
    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping(PATH)
    public ResponseEntity<List<ReservationTimeResponse>> read() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAll();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @PostMapping(PATH)
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest request) {
        long id = reservationTimeService.add(request);
        ReservationTimeResponse reservationTime = reservationTimeService.findOne(id);
        return ResponseEntity.ok().body(reservationTime);
    }

    @DeleteMapping(PATH + "/{id}")
    public void delete(@PathVariable("id") Long id) {
        reservationTimeService.delete(id);
    }
}
