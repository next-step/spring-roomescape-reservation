package nextstep.app.web.reservation.port.web;

import nextstep.app.web.reservation.application.dto.CreateReservationResult;
import nextstep.app.web.reservation.application.usecase.CreateReservationUseCase;
import nextstep.app.web.reservation.query.ReservationQuery;
import nextstep.domain.reservation.domain.model.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;
    private final ReservationQuery reservationQuery;

    public ReservationController(CreateReservationUseCase createReservationUseCase, ReservationQuery reservationQuery) {
        this.createReservationUseCase = createReservationUseCase;
        this.reservationQuery = reservationQuery;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest request) {
        CreateReservationResult result = createReservationUseCase.create(request.toCommand());
        return ResponseEntity.created(URI.create("/reservations/" + result.id())).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> findAll(@RequestParam String date) {
        List<Reservation> reservations = reservationQuery.findAllByDate(date);
        return ResponseEntity.ok(ReservationResponse.listFrom(reservations));
    }
}
