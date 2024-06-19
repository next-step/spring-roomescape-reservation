package roomescape.reservation.ui.dto;

import roomescape.reservationtime.ui.dto.ReservationTimeResponse;
import roomescape.theme.ui.dto.ThemeResponse;
import roomescape.reservation.domain.entity.Reservation;

import java.util.List;

public class ReservationResponse {
    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTimeResponse time;
    private final ThemeResponse theme;

    private ReservationResponse(Long id, String name, String date, ReservationTimeResponse time, ThemeResponse theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeResponse.from(reservation.getTime()),
                ThemeResponse.from(reservation.getTheme())
        );
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

    public static List<ReservationResponse> fromReservations(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::from).toList();
    }
}
