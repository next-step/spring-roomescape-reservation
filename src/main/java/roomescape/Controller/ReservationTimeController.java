package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationTimeRequest;
import roomescape.DTO.ReservationTimeResponse;
import roomescape.Service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping(path = "/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    @Autowired
    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationTimeResponse>> read() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(reservationTimes);
    }

    @PostMapping()
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest request) {
        long id = reservationTimeService.add(request);
        ReservationTimeResponse reservationTime = reservationTimeService.findOne(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
