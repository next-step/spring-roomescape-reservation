package nextstep.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest request) {
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.created(URI.create("/reservations/" + response.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservation(@RequestParam("date") String date) {
        List<ReservationResponse> responses = reservationService.findReservationByDate(date);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelReservation(@RequestParam("date") String date, @RequestParam("time") String time) {
        ReservationDeleteRequest request = ReservationDeleteRequest.of(date, time);
        reservationService.cancelReservation(request);
        return ResponseEntity.noContent().build();
    }
}
