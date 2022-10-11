package nextstep.ui;

import static java.util.stream.Collectors.toList;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import nextstep.Reservation;
import nextstep.ui.request.ReservationCreateRequest;
import nextstep.ui.response.ReservationResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

    private final AtomicLong id = new AtomicLong(1);
    private final List<Reservation> reservations;

    public ReservationController() {
        this.reservations = new ArrayList<>();
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = new Reservation(
            id.getAndIncrement(),
            request.getDate(),
            request.getTime(),
            request.getName()
        );

        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservationsByDate(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return ResponseEntity.ok(ReservationResponse.of(findReservationsByDate(date)));
    }

    private List<Reservation> findReservationsByDate(LocalDate date) {
        return reservations.stream()
            .filter(it -> Objects.equals(it.getDate(), date))
            .collect(toList());
    }
}
