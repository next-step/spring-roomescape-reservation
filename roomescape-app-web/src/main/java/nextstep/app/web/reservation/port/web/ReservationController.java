package nextstep.app.web.reservation.port.web;

import nextstep.app.web.reservation.application.dto.CreateReservationResult;
import nextstep.app.web.reservation.application.usecase.CreateReservationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;

    public ReservationController(CreateReservationUseCase createReservationUseCase) {
        this.createReservationUseCase = createReservationUseCase;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest request) {
        CreateReservationResult result = createReservationUseCase.create(request.toCommand());
        return ResponseEntity.created(URI.create("/reservations/" + result.id())).build();
    }
}
