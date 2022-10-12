package nextstep.controller;

import nextstep.dto.ReservationCreateRequest;
import nextstep.dto.ReservationFindAllResponse;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        Long reservationId = reservationService.createReservation(reservationCreateRequest);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @GetMapping
    public ResponseEntity<ReservationFindAllResponse> findAll(@RequestParam String date) {
        ReservationFindAllResponse reservationFindAllResponse = reservationService.findAllReservations(date);
        return ResponseEntity.ok(reservationFindAllResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String date, @RequestParam String time) {
        reservationService.deleteReservation(date, time);
        return ResponseEntity.noContent().build();
    }
}
