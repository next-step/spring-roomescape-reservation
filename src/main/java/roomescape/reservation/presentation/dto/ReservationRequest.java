package roomescape.reservation.presentation.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationRequest {

    private final String name;
    private final String date;
    private final Long timeId;
    private final Long themeId;

    @JsonCreator
    public ReservationRequest(
            @JsonProperty("name") String name,
            @JsonProperty("date") String date,
            @JsonProperty("timeId") Long timeId,
            @JsonProperty("themeId") Long themeId) {
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
