package roomescape.reservation.ui.dto;

public class ReservationRequest {
    private String name;
    private String date;
    private Long timeId;
    private Long themeId;

    private ReservationRequest() {
    }

    private ReservationRequest(String name, String date, Long timeId, Long themeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.themeId = themeId;
    }

    public static ReservationRequest create(String name, String date, Long timeId, Long themeId) {
        return new ReservationRequest(name, date, timeId, themeId);
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
