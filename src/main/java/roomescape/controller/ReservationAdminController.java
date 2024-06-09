package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;
import roomescape.model.ReservationCreateDto;
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
        return "admin/reservation-time";
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateDto> createReservation(@RequestBody ReservationCreateDto reservationCreateDto) {

        ReservationCreateDto newReservationCreateDto = reservationAdminService.createReservation(reservationCreateDto);

        return ResponseEntity.ok().body(newReservationCreateDto);
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
