package roomescape.application.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.api.dto.request.CreateReservationTimeRequest;
import roomescape.application.api.dto.response.CreateReservationTimeResponse;
import roomescape.application.service.ReservationTimeService;
import roomescape.domain.reservationtime.ReservationTime;

@RestController
public class ReservationTimeCommandApi {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeCommandApi(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<CreateReservationTimeResponse> createReservationTime(
            @RequestBody CreateReservationTimeRequest createReservationTimeRequest
    ) {
        ReservationTime reservationTime =
                reservationTimeService.createReservationTime(createReservationTimeRequest.toCreateReservationTimeCommand());

        return ResponseEntity.ok(CreateReservationTimeResponse.from(reservationTime));
    }
}
