package roomescape.dto.reservation.create;

import roomescape.domain.Reservation;
import roomescape.dto.theme.ThemeResponse;
import roomescape.dto.time.ReservationTimeResponse;

import java.time.LocalDate;

public class ReservationCreateResponse {

    private Long id;
    private LocalDate date;
    private String name;
    private ReservationTimeResponse time;

    private ThemeResponse theme;

    public ReservationCreateResponse() {
    }

    public ReservationCreateResponse(Long id, LocalDate date, String name, ReservationTimeResponse time, ThemeResponse theme) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.time = time;
        this.theme = theme;
    }
    public static ReservationCreateResponse fromEntity(Reservation reservation) {
        return new ReservationCreateResponse(reservation.getId(), reservation.getDate(), reservation.getName(),
                new ReservationTimeResponse(reservation.getTime()), ThemeResponse.fromEntity(reservation.getTheme()));
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public ReservationTimeResponse getTime() {
        return time;
    }

    public ThemeResponse getTheme() {
        return theme;
    }
}
