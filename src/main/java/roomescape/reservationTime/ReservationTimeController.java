package roomescape.reservationTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> getTimes() {
        final List<ReservationTimeResponseDto> reservationTimeResponseDtos = reservationTimeService.getTimes();
        return ResponseEntity.ok().body(reservationTimeResponseDtos);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> addTime(@RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        final ReservationTimeResponseDto time = reservationTimeService.addTime(reservationTimeRequestDto);
        return ResponseEntity.ok().body(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.ok().build();
    }

}
