package roomescape.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ReservationTimeCreateRequestDto {
    @Positive
    private Long id;
    @NotBlank
    private String startAt;

    public String getStartAt() {
        return startAt;
    }

}
