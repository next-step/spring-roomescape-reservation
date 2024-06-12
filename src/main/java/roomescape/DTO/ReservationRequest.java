package roomescape.DTO;

import roomescape.Entity.Reservation;

public class ReservationRequest {
    private String name;
    private String date;
    private Long timeId;
    private Long themeId;

    public ReservationRequest() {

    }

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
