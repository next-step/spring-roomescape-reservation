package nextstep.reservation.controller;

import java.net.URI;
import java.util.List;
import nextstep.reservation.Reservation;
import nextstep.reservation.service.ReservationService;
import nextstep.reservation.controller.request.CreateReservationRequest;
import nextstep.reservation.controller.request.RemoveReservationRequest;
import nextstep.reservation.controller.request.ReservationListRequest;
import nextstep.reservation.exception.AlreadyReservedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity reserve(@RequestBody CreateReservationRequest request) {
        try {
            long id = reservationService.reserve(request.getDate(), request.getTime(), request.getName());
            return ResponseEntity.created(URI.create("/reservations/" + id)).build();
        } catch (AlreadyReservedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations(ReservationListRequest request) {
        return ResponseEntity.ok(reservationService.getReservationList(request.getDate()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(RemoveReservationRequest request) {
        reservationService.removeReservation(request.getDate(), request.getTime());
        return ResponseEntity.noContent().build();
    }
}
