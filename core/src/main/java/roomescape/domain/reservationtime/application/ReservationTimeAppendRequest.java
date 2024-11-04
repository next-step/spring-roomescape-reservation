package roomescape.domain.reservationtime.application;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ReservationTimeAppendRequest {

    @NotBlank
    private LocalTime startAt;

    public ReservationTimeAppendRequest() {
    }

    public ReservationTimeAppendRequest(final LocalTime startAt) {
        this.startAt = startAt;
    }
}
