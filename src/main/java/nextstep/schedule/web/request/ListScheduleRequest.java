package nextstep.schedule.web.request;

public class ListScheduleRequest {

    private final Long themeId;
    private final String date;

    public ListScheduleRequest(Long themeId, String date) {
        this.themeId = themeId;
        this.date = date;
    }

    public Long getThemeId() {
        return themeId;
    }

    public String getDate() {
        return date;
    }
}
