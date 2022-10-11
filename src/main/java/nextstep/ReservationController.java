package nextstep;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    List<Reservation> reservations = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = request.toObject();
        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }
}
