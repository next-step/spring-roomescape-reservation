package roomescape.reservationtime.ui.dto;

import jakarta.validation.constraints.NotBlank;

public class ReservationTimeRequest {
    @NotBlank
    private String startAt;

    private ReservationTimeRequest() {
    }

    private ReservationTimeRequest(String startAt) {
        this.startAt = startAt;
    }

    public static ReservationTimeRequest create(String startAt) {
        return new ReservationTimeRequest(startAt);
    }

    public String getStartAt() {
        return this.startAt;
    }
}
