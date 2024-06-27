package roomescape.application.presentation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.presentation.api.dto.response.FindAllReservationTimesResponse;
import roomescape.application.service.ReservationTimeService;
import roomescape.domain.reservationtime.ReservationTimes;

import java.util.List;

@RestController
public class ReservationTimeQueryApi {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeQueryApi(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<FindAllReservationTimesResponse>> findAll() {
        ReservationTimes reservationTimes = reservationTimeService.findAll();

        return ResponseEntity.ok(FindAllReservationTimesResponse.toFindAllReservationResponses(reservationTimes));
    }
}
