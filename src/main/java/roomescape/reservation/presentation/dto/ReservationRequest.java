package roomescape.reservation.presentation.dto;

public class ReservationRequest {

    private final String name;
    private final String date;
    private final Long timeId;
    private final Long themeId;

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
