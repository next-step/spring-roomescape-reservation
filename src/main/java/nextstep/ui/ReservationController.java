package nextstep.ui;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.domain.Reservations;
import nextstep.ui.request.ReservationCreateRequest;
import nextstep.ui.response.ReservationResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

    private final Reservations reservations;

    public ReservationController() {
        this.reservations = new Reservations();
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = reservations.add(
            request.getDate(),
            request.getTime(),
            request.getName()
        );

        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservationsByDate(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return ResponseEntity.ok(ReservationResponse.of(reservations.findAllByDate(date)));
    }

    @DeleteMapping
    public ResponseEntity<Void> getReservationsByDate(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {
        reservations.removeByDateTime(date, time);
        return ResponseEntity.noContent().build();
    }
}
