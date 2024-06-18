package roomescape.presentation.controller.api.reservationTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.service.reservationTime.ReservationTimeService;
import roomescape.presentation.dto.request.CreateReservationTimeRequest;
import roomescape.presentation.dto.response.CreateReservationTimeResponse;
import roomescape.presentation.dto.response.GetReservationTimeListResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeApiController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<GetReservationTimeListResponse>> getReservationTimes() {
        return ResponseEntity.ok().body(
                reservationTimeService.getReservationTimes().stream()
                        .map(reservationTime -> GetReservationTimeListResponse.builder()
                                .id(reservationTime.getId())
                                .startAt(reservationTime.getStartAt())
                                .build())
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<CreateReservationTimeResponse> createReservationTime(
            @RequestBody CreateReservationTimeRequest request
    ) {

        return ResponseEntity.ok().body(
                CreateReservationTimeResponse.of(
                        reservationTimeService.createReservationTime(request.getStartAt())
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(
            @PathVariable Long id
    ) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
