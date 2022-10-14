package nextstep.schedules.controller.response;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.schedules.Schedule;
import nextstep.themes.Themes;

public class FindScheduleResponse {

    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final ThemeResponse theme;

    public FindScheduleResponse(Long id, LocalDate date, LocalTime time, ThemeResponse theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public static FindScheduleResponse from(Schedule schedule) {
        Themes themes = schedule.getThemes();
        ThemeResponse themeResponse = new ThemeResponse(themes.getId(), themes.getName(), themes.getDesc(), themes.getPrice());
        return new FindScheduleResponse(schedule.getId(), schedule.getDate(), schedule.getTime(), themeResponse);
    }

    public static class ThemeResponse {
        private final Long id;
        private final String name;
        private final String desc;
        private final Long price;

        public ThemeResponse(Long id, String name, String desc, Long price) {
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.price = price;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }

        public Long getPrice() {
            return price;
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public ThemeResponse getTheme() {
        return theme;
    }
}
