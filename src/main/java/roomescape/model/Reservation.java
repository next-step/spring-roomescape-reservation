package roomescape.model;

import java.util.concurrent.atomic.AtomicLong;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Long timeId;
    private Long themeId;
    private String themeName;
    private String startAt;

    public Reservation(long id, String name, String themeName, String date, String startAt) {
        this.id = id;
        this.name = name;
        this.themeName = themeName;
        this.date = date;
        this.startAt = startAt;
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

    public Long getTimeId() {
        return timeId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public String getStartAt() {
        return startAt;
    }
}
