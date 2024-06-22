package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.time.ReservationTimeRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.dto.time.create.ReservationTimeCreateResponse;
import roomescape.exception.custom.DuplicatedReservationTimeException;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findTimes() {
        List<ReservationTimeResponse> times = reservationTimeService.findTimes();
        return ResponseEntity.ok().body(times);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeCreateResponse> create(@Valid @RequestBody ReservationTimeRequest dto) {
        ReservationTimeCreateResponse time = reservationTimeService.createTime(dto);
        return ResponseEntity.ok().body(time);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(DuplicatedReservationTimeException.class)
    public ResponseEntity<String> handleDuplicatedReservationTimeException(DuplicatedReservationTimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
