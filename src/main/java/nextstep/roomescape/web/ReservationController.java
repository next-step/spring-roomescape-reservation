package nextstep.roomescape.web;

import java.net.URI;
import java.util.List;
import nextstep.roomescape.service.ReservationService;
import nextstep.roomescape.web.dto.ReservationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<URI> register(@RequestBody ReservationDto request) {
        final int registeredId = reservationService.register(request);
        return ResponseEntity.created(URI.create("/reservations/" + registeredId)).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<Void> delete(Integer id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
