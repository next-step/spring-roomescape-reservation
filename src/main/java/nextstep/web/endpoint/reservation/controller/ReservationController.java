package nextstep.web.endpoint.reservation.controller;

import nextstep.web.endpoint.reservation.request.ReservationCreateRequest;
import nextstep.web.endpoint.reservation.request.ReservationDeleteRequest;
import nextstep.web.endpoint.reservation.request.ReservationsSearchRequest;
import nextstep.web.endpoint.reservation.response.ReservationResponse;
import nextstep.web.endpoint.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest request) {
        Long id = reservationService.create(request);
        URI location = URI.create("/reservations/" + id);

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> findAllByDate(ReservationsSearchRequest request) {
        List<ReservationResponse> response = reservationService.findAllByDate(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<Void> cancel(ReservationDeleteRequest request) {
        reservationService.cancelByDateAndTime(request);

        return ResponseEntity.noContent().build();
    }
}
