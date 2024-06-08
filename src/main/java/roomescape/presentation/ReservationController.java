package roomescape.presentation;

import static roomescape.application.ReservationServiceInput.createReservationServiceInput;
import static roomescape.presentation.dto.ReservationResponse.createReservationResponse;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.ReservationService;
import roomescape.application.ReservationServiceInput;
import roomescape.application.ReservationServiceOutput;
import roomescape.presentation.dto.ReservationRequest;
import roomescape.presentation.dto.ReservationResponse;

@RestController
public class ReservationController {

    private static final String NAMESPACE = "/reservations";

    private final ReservationService reservationService;

    private ReservationController(ReservationService reservation) {
        this.reservationService = reservation;
    }

    @PostMapping(NAMESPACE)
    public ResponseEntity<ReservationResponse> createReservations(
        @RequestBody ReservationRequest request) {
        ReservationServiceInput input = createReservationServiceInput(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createReservationResponse(reservationService.create(input)));
    }

    @GetMapping(NAMESPACE)
    public ResponseEntity<List<ReservationResponse>> retrieveReservations() {
        List<ReservationServiceOutput> outputs = reservationService.retrieveReservations();
        List<ReservationResponse> responses = outputs.stream()
            .map(ReservationResponse::createReservationResponse)
            .toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping(NAMESPACE + "/{id}")
    public ResponseEntity<ReservationResponse> deleteReservations(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
