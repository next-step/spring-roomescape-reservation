package roomescape.reservationTime.dto;

import jakarta.validation.constraints.NotBlank;
import roomescape.error.RoomescapeErrorMessage;

public class ReservationTimeRequest {

    @NotBlank(message = "시간" + RoomescapeErrorMessage.BLANK_INPUT_VALUE_EXCEPTION)
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
