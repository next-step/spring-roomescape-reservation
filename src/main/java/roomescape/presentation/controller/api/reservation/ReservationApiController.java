package roomescape.presentation.controller.api.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.service.reservation.ReservationService;
import roomescape.presentation.dto.request.CreateReservationRequest;
import roomescape.presentation.dto.response.CreateReservationResponse;
import roomescape.presentation.dto.response.GetReservationListResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {
    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<GetReservationListResponse>> getReservations() {
        return ResponseEntity.ok().body(
                reservationService.getReservations().stream()
                        .map(reservation -> GetReservationListResponse.builder()
                                .id(reservation.getId())
                                .name(reservation.getName())
                                .date(reservation.getDate())
                                .time(reservation.getTime())
                                .build())
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<CreateReservationResponse> createReservation(
            @RequestBody CreateReservationRequest request
    ) {
        return ResponseEntity.ok().body(
                CreateReservationResponse.of(
                        reservationService.createReservation(request.toCommand())
                )
        );
    }
}
