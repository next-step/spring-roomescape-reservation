package roomescape.dto.time;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.dto.reservation.create.ReservationCreateRequest;

public class ReservationTimeRequest {

    @NotBlank(message = "시간을 입력해주세요.")
    private String startAt;

    public ReservationTimeRequest() {
    }

    public ReservationTimeRequest(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }

}
