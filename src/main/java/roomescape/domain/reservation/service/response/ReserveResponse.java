package roomescape.domain.reservation.service.response;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.domain.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReserveResponse {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    @Builder
    private ReserveResponse(
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

    public static ReserveResponse from(final Reservation reservation) {
        return ReserveResponse.builder()
                .id(reservation.getId())
                .name(reservation.getName().getValue())
                .date(reservation.fetchReservationDate())
                .time(reservation.fetchReservationTime())
                .build();
    }

}
