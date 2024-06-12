package roomescape.DTO;

import roomescape.Entity.Reservation;

import java.util.List;

public class ReservationResponse {
    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTimeResponse time;
    private final ThemeResponse theme;

    public ReservationResponse(Long id, String name, String date, ReservationTimeResponse time, ThemeResponse theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = new ReservationTimeResponse(reservation.getTime());
        this.theme = new ThemeResponse(reservation.getTheme());
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

    public ReservationTimeResponse getTime() {
        return time;
    }

    public ThemeResponse getTheme() {
        return theme;
    }

    public static List<ReservationResponse> toDTOList(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::new).toList();
    }
}
