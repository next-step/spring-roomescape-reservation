package nextstep.dto.schedule;

public class ScheduleCreateRequest {
    private final Long themeId;
    private final String date;
    private final String time;

    public ScheduleCreateRequest(Long themeId, String date, String time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public Long getThemeId() {
        return themeId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
