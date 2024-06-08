package roomescape.reservation.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.web.dto.ReservationRequest;
import roomescape.reservation.web.dto.ReservationResponse;
import roomescape.time.domain.Time;
import roomescape.time.service.TimeService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final TimeService timeService;

    public ReservationController(ReservationService reservationService, TimeService timeService) {
        this.reservationService = reservationService;
        this.timeService = timeService;
    }

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> reservations = reservationService.findAll();
        List<ReservationResponse> responses = reservations.stream().map(ReservationResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest request) {
        Time time = timeService.findById(request.getTimeId());
        Reservation reservation = reservationService.save(new Reservation(null, request.getName(), request.getDate(), time));
        return ResponseEntity.ok().body(new ReservationResponse(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
