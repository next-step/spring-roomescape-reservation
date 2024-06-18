package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;
import roomescape.model.ReservationCreateDto;
import roomescape.model.ReservationCreateResponseDto;
import roomescape.service.ReservationAdminService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationAdminApiController {

    private final ReservationAdminService reservationAdminService;

    public ReservationAdminApiController(ReservationAdminService reservationAdminService) {
        this.reservationAdminService = reservationAdminService;
    }

    @PostMapping
    public ResponseEntity<ReservationCreateResponseDto> createReservation(@RequestBody ReservationCreateDto reservationCreateDto) {

        ReservationCreateResponseDto reservationResponse = reservationAdminService.createReservation(reservationCreateDto);

        return ResponseEntity.ok(reservationResponse);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservation() {
        List<Reservation> reservations = reservationAdminService.getReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        reservationAdminService.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}
