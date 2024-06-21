package roomescape.application.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.api.dto.request.CreateReservationTimeRequest;
import roomescape.application.api.dto.response.CreateReservationTimeResponse;
import roomescape.application.service.ReservationTimeService;
import roomescape.application.service.command.DeleteReservationTimeCommand;
import roomescape.domain.reservationtime.ReservationTime;

import java.net.URI;

@RestController
public class ReservationTimeCommandApi {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeCommandApi(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<CreateReservationTimeResponse> createReservationTime(
            @RequestBody @Valid CreateReservationTimeRequest request
    ) {
        ReservationTime reservationTime =
                reservationTimeService.createReservationTime(request.toCreateReservationTimeCommand());

        return ResponseEntity
                .created(URI.create(String.format("/times/%d", reservationTime.getId())))
                .body(CreateReservationTimeResponse.from(reservationTime));
    }

    @DeleteMapping("/times/{reservationTimeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long reservationTimeId) {
        reservationTimeService.deleteReservationTime(new DeleteReservationTimeCommand(reservationTimeId));

        return ResponseEntity.noContent().build();
    }
}
