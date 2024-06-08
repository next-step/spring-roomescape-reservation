package roomescape.dto.response;

public class ReservationResponse {

    private Long id;
    private String name;
    private String date;
    private String timeStartAt;
    private String themeName;

    public ReservationResponse(Long id, String name, String date, String timeStartAt, String themeName) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeStartAt = timeStartAt;
        this.themeName = themeName;
    }

    public ReservationResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTimeStartAt() {
        return timeStartAt;
    }

    public String getThemeName() {
        return themeName;
    }

    public String getDate() {
        return date;
    }
}
