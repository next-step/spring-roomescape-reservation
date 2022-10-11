package nextstep.ui;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import nextstep.Reservation;
import nextstep.ui.request.ReservationCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

    private final List<Reservation> reservations;

    public ReservationController() {
        this.reservations = new ArrayList<>();
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = new Reservation(
            request.getDate(),
            request.getTime(),
            request.getName()
        );

        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/1")).build();
    }
}
