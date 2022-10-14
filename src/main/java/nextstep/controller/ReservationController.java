package nextstep.controller;

import java.net.URI;
import java.util.List;
import nextstep.controller.dto.ReservationRequest;
import nextstep.controller.dto.ReservationResponse;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> make(ReservationRequest request) {
        Integer reservationId = reservationService.make(request);

        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> check(@RequestParam String date) {
        List<ReservationResponse> responses = reservationService.check(date);

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancel(@RequestParam String date, @RequestParam String time) {
        reservationService.cancel(date, time);

        return ResponseEntity.noContent().build();
    }
}
