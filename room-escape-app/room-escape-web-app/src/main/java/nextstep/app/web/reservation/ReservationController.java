package nextstep.app.web.reservation;

import nextstep.core.reservation.in.ReservationResponse;
import nextstep.core.reservation.in.ReservationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequestMapping("/reservations")
@RestController
class ReservationController {
    private final ReservationUseCase useCase;

    public ReservationController(ReservationUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservationCreateWebRequest request) {
        ReservationResponse reservation = useCase.create(request.to());
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationWebResponse>> list(@RequestParam String date) {
        List<ReservationResponse> reservations = useCase.findAllByDate(LocalDate.parse(date));
        return ResponseEntity.ok(
                reservations.stream()
                        .map(ReservationWebResponse::from)
                        .toList()
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String date, @RequestParam String time) {
        useCase.deleteByDateAndTime(LocalDate.parse(date), LocalTime.parse(time));
        return ResponseEntity.noContent().build();
    }
}
