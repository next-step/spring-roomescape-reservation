package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservation.ReservationsResponse;
import roomescape.dto.reservation.create.ReservationCreateRequest;
import roomescape.dto.reservation.create.ReservationCreateResponse;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationsResponse>> findReservations() {
        List<ReservationsResponse> list = reservationService.findReservations();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationCreateRequest dto) {
        ReservationCreateResponse reservation = reservationService.createReservation(dto);
        return ResponseEntity.created(URI.create("/reservation/" + reservation.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }


}
