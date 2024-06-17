package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;
import roomescape.model.ReservationCreateDto;
import roomescape.model.ReservationCreateResponseDto;
import roomescape.service.ReservationAdminService;

import java.util.List;

@Controller
public class ReservationAdminController {

    private final ReservationAdminService reservationAdminService;

    public ReservationAdminController(ReservationAdminService reservationAdminService) {
        this.reservationAdminService = reservationAdminService;
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "admin/reservation";
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateResponseDto> createReservation(@RequestBody ReservationCreateDto reservationCreateDto) {

        ReservationCreateResponseDto reservationResponse = reservationAdminService.createReservation(reservationCreateDto);

        return ResponseEntity.ok(reservationResponse);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservation() {
        List<Reservation> reservations = reservationAdminService.getReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        reservationAdminService.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}
