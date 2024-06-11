package roomescape.reservation.presentation.dto;

import lombok.Builder;
import roomescape.theme.domain.Theme;
import roomescape.time.domain.Time;

@Builder
public class ReservationResponse {

    private final Long id;
    private final String name;
    private final String date;
    private final Time time;
    private final Theme theme;

    public ReservationResponse(Long id, String name, String date, Time time, Theme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
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

    public Time getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }
}
