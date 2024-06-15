package roomescape.application.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.api.dto.request.CreateReservationTimeRequest;
import roomescape.application.api.dto.response.CreateReservationTimeResponse;
import roomescape.application.service.ReservationTimeService;
import roomescape.application.service.command.DeleteReservationTimeCommand;
import roomescape.domain.reservationtime.ReservationTime;

@RestController
public class ReservationTimeCommandApi {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeCommandApi(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<CreateReservationTimeResponse> createReservationTime(
            @RequestBody @Valid CreateReservationTimeRequest createReservationTimeRequest
    ) {
        ReservationTime reservationTime =
                reservationTimeService.createReservationTime(createReservationTimeRequest.toCreateReservationTimeCommand());

        return ResponseEntity.ok(CreateReservationTimeResponse.from(reservationTime));
    }

    @DeleteMapping("/times/{reservationTimeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long reservationTimeId) {
        reservationTimeService.deleteReservationTime(new DeleteReservationTimeCommand(reservationTimeId));

        return ResponseEntity.ok().build();
    }
}
