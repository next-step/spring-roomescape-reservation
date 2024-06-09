package roomescape.dto.request;

public class ReservationRequest {
    private Long id;
    private String name;
    private String date;
    private String timeId;
    private String themeId;

    public ReservationRequest() {
    }

    public ReservationRequest(Long id, String name, String date, String timeId, String themeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.themeId = themeId;
    }

    public ReservationRequest(String name, String date, String timeId, String themeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.themeId = themeId;
    }

    public Long getId() {
        return id;
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
