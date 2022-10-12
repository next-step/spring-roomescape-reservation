package nextstep;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDatabase reservations = new ReservationDatabase();

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest request) {
        if (reservations.existsReservation(request.getDate(), request.getTime())) {
            throw new IllegalStateException("해당 일시에 이미 예약이 존재하여 예약이 불가능합니다.");
        }
        Reservation reservation = request.toObject();
        reservations.save(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservation(@RequestParam("date") String date) {
        List<ReservationResponse> findReservations = reservations.findByDate(LocalDate.parse(date)).stream()
                .map(it -> new ReservationResponse(it.getId(), it.getDate(), it.getTime(), it.getName()))
                .toList();
        return ResponseEntity.ok(findReservations);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(@RequestParam("date") String date, @RequestParam("time") String time) {
        ReservationDeleteRequest request = ReservationDeleteRequest.of(date, time);
        reservations.deleteByDateAndTime(request.getDate(), request.getTime());
        return ResponseEntity.noContent().build();
    }
}
