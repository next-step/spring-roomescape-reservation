package roomescape.presentation.controller.api.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.service.reservation.ReservationService;
import roomescape.presentation.dto.response.GetReservationListResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {
    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService){
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
}
