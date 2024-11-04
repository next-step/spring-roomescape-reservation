package roomescape.domain.reservation.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.application.dto.ReservationTimeThemeDto;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservationtime.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReserveHttpResponse {

    private final Long id;

    private final String name;

    private final LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime time;

    @Builder
    private ReserveHttpResponse(
            final Long id,
            final String name,
            final LocalDate date,
            final LocalTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReserveHttpResponse from(final ReservationTimeThemeDto dto) {
        final Reservation reservation = dto.getReservation();
        final ReservationTime time = dto.getTime();

        return ReserveHttpResponse.builder()
                .id(reservation.getId())
                .name(reservation.getName().getValue())
                .date(reservation.getDate().getValue())
                .time(time.getStartAt())
                .build();
    }
}
