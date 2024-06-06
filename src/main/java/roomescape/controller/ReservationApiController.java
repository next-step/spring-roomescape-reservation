package roomescape.controller;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationApiController {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationApiController(ReservationService reservationService,
                                    ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = reservationService.read();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        ReservationResponse newReservation = reservationService.create(request);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(value = "id") Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> times(@RequestBody ReservationTime reservationTime) {
        ReservationTime newReservationTime = reservationTimeService.create(reservationTime);
        return ResponseEntity.ok().body(newReservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> times() {
        List<ReservationTime> reservationTimes = reservationTimeService.read();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable(value = "id") Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
