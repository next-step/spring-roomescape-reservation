package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.time.ReservationTimeRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.dto.time.create.ReservationTimeCreateResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.ErrorCodeResponse;
import roomescape.exception.custom.DuplicatedReservationTime;
import roomescape.service.ReservationTimeService;

import java.net.URI;
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
        return ResponseEntity.created(URI.create("/times/" + time.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(DuplicatedReservationTime.class)
    public ResponseEntity<String> handleDuplicatedReservationTimeException(DuplicatedReservationTime e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
