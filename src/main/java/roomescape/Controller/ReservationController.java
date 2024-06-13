package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationRequest;
import roomescape.DTO.ReservationResponse;
import roomescape.Service.ReservationService;

import java.util.List;

@RestController
public class ReservationController {
    private final String PATH = "reservations";
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(PATH)
    public ResponseEntity<List<ReservationResponse>> read() {
        List<ReservationResponse> reservations = reservationService.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping(PATH)
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        Long reservationId = reservationService.make(request);
        ReservationResponse reservation = reservationService.findOne(reservationId);
        return ResponseEntity.ok().body(reservation);

    }

    @DeleteMapping(PATH + "/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        reservationService.cancel(id);
        return ResponseEntity.ok().build();
    }
}
