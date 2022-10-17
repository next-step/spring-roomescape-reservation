package nextstep.presentation.dto;

public class ScheduleResponse {

    private Long id;
    private ThemeResponse theme;
    private String date;
    private String time;

    private ScheduleResponse() {
    }

    public ScheduleResponse(Long id, ThemeResponse theme, String date, String time) {
        this.id = id;
        this.theme = theme;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public ThemeResponse getTheme() {
        return theme;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
