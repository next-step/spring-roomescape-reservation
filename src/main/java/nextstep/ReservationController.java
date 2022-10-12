package nextstep;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    List<Reservation> reservations = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest request) {
        if (existsReservation(request.getDate(), request.getTime())) {
            throw new IllegalStateException("해당 일시에 이미 예약이 존재하여 예약이 불가능합니다.");
        }
        Reservation reservation = request.toObject();
        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    private boolean existsReservation(LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(it -> Objects.equals(it.getDate(), date) && Objects.equals(it.getTime(), time));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservation(@RequestParam("date") String date) {
        List<ReservationResponse> findReservations = reservations.stream()
                .filter(it -> Objects.equals(it.getDate(), LocalDate.parse(date)))
                .map(it -> new ReservationResponse(it.getId(), it.getDate(), it.getTime(), it.getName()))
                .toList();
        return ResponseEntity.ok(findReservations);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(@RequestParam("date") String date, @RequestParam("time") String time) {
        ReservationDeleteRequest request = ReservationDeleteRequest.of(date, time);
        reservations.stream()
                .filter(it -> Objects.equals(it.getDate(), request.getDate()) && Objects.equals(it.getTime(), request.getTime()))
                .findFirst()
                .ifPresent(reservations::remove);
        return ResponseEntity.noContent().build();
    }
}
