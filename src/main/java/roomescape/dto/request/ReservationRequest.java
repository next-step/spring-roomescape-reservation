package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class ReservationRequest {

    @NotBlank
    private String name;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    @NotBlank
    private String timeId;

    @NotBlank
    private String themeId;

    public ReservationRequest() {
    }

    public ReservationRequest(String name, String date, String timeId, String themeId) {
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

    public String getTimeId() {
        return timeId;
    }

    public String getThemeId() {
        return themeId;
    }
}
