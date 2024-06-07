package roomescape.reservation.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.web.dto.ReservationRequest;
import roomescape.reservation.web.dto.ReservationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/admin/reservation")
    String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> reservations = reservationService.findAll();
        List<ReservationResponse> responses = reservations.stream().map(ReservationResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/reservations")
    @ResponseBody
    ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.save(request.toEntity());
        return ResponseEntity.ok().body(new ReservationResponse(reservation));
    }
}
