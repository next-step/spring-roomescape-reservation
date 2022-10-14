package nextstep.reservation.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.reservation.controller.request.CreateReservationRequest;
import nextstep.reservation.controller.request.RemoveReservationRequest;
import nextstep.reservation.controller.request.ReservationListRequest;
import nextstep.reservation.controller.response.ReservationFindResponse;
import nextstep.reservation.exception.AlreadyReservedException;
import nextstep.reservation.service.ReservationService;
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
    public ResponseEntity<List<ReservationFindResponse>> getReservations(ReservationListRequest request) {
        List<ReservationFindResponse> response = reservationService.getReservationList(request.getDate())
                                                                   .stream()
                                                                   .map(ReservationFindResponse::from)
                                                                   .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(RemoveReservationRequest request) {
        reservationService.removeReservation(request.getDate(), request.getTime());
        return ResponseEntity.noContent().build();
    }
}
