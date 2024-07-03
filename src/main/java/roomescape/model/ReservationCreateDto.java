package roomescape.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ReservationCreateDto {
    @NotBlank
    private String date;
    @NotBlank
    private String name;
    @Positive
    private Long timeId;
    @Positive
    private Long themeId;

    public ReservationCreateDto(String date, String name, Long timeId) {
        this.date = date;
        this.name = name;
        this.timeId = timeId;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getTimeId() {
        return timeId;
    }

    public Long getThemeId() {
        return themeId;
    }
}
