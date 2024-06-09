package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class ReservationTimeRequest {

    @NotBlank
    @DateTimeFormat(pattern = "mm:ss")
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
