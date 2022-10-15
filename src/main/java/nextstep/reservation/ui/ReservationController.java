package nextstep.reservation.ui;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reservation.application.ReservationService;
import nextstep.reservation.ui.request.ReservationCreateRequest;
import nextstep.reservation.ui.response.ReservationResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest request) {
        ReservationResponse response = reservationService.create(request);
        return ResponseEntity.created(URI.create("/reservations/" + response.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservationsByDate(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return ResponseEntity.ok(reservationService.findAllByDate(date));
    }

    @DeleteMapping
    public ResponseEntity<Void> getReservationsByDate(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {
        reservationService.deleteByDateTime(date, time);
        return ResponseEntity.noContent().build();
    }
}
