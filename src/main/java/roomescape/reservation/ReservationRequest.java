package roomescape.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReservationRequest {

    @NotBlank(message = "이름")
    private String name;

    @NotBlank(message = "날짜")
    private String date;

    @NotNull(message = "시간")
    private Long timeId;

    @NotNull(message = "테마")
    private Long themeId;

    public ReservationRequest(String name, String date, Long timeId, Long themeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.themeId = themeId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }

    public Long getThemeId() {
        return themeId;
    }
}
