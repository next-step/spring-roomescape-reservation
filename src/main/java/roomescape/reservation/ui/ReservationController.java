package roomescape.reservation.ui;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.ui.dto.ReservationRequest;
import roomescape.reservation.ui.dto.ReservationResponse;
import roomescape.reservation.application.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> read() {
        List<ReservationResponse> reservations = reservationService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody @Valid ReservationRequest request) {
        Long reservationId = reservationService.make(request);
        ReservationResponse reservation = reservationService.findOne(reservationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        reservationService.cancel(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
