package roomescape.api.reservation.api.response;

import lombok.Builder;
import lombok.Getter;
import roomescape.api.reservation.application.dto.ReservationTimeThemeDto;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class ReservationQueryHttpResponse {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeDto time;
    private final ThemeDto theme;

    @Builder
    private ReservationQueryHttpResponse(
            final Long id,
            final String name,
            final LocalDate date,
            final ReservationTimeDto time,
            final ThemeDto theme
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public static List<ReservationQueryHttpResponse> from(final List<ReservationTimeThemeDto> reservationTimeThemeDtos) {
        return reservationTimeThemeDtos.stream()
                .map(ReservationQueryHttpResponse::from)
                .toList();
    }

    public static ReservationQueryHttpResponse from(final ReservationTimeThemeDto reservationTimeThemeDto) {
        final Reservation reservation = reservationTimeThemeDto.getReservation();
        final ReservationTime reservationTime = reservationTimeThemeDto.getTime();

        return ReservationQueryHttpResponse.builder()
                .id(reservation.getId())
                .name(reservation.getName().getValue())
                .date(reservation.getDate().getValue())
                .time(ReservationTimeDto.from(reservationTime))
                .theme(new ThemeDto(reservationTimeThemeDto.getTheme().getName()))
                .build();
    }

    @Getter
    private static class ReservationTimeDto {

        private final LocalTime startAt;

        @Builder
        private ReservationTimeDto(final LocalTime startAt) {
            this.startAt = startAt;
        }

        public static ReservationTimeDto from(final ReservationTime time) {
            return ReservationTimeDto.builder()
                    .startAt(time.getStartAt())
                    .build();
        }
    }

    @Getter
    private static class ThemeDto {

        private final String name;

        @Builder
        private ThemeDto(final String name) {
            this.name = name;
        }

        public ThemeDto from(final Theme theme) {
            return ThemeDto.builder()
                    .name(theme.getName())
                    .build();
        }
    }
}
