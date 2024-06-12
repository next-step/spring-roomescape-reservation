package roomescape.reservationTime.dto;

import jakarta.validation.constraints.NotBlank;

public class ReservationTimeRequest {

    @NotBlank(message = "시간")
    private String startAt;

    public ReservationTimeRequest(String startAt) {
        this.startAt = startAt;
    }

    public ReservationTimeRequest() {
    }

    public String getStartAt() {
        return startAt;
    }
}
