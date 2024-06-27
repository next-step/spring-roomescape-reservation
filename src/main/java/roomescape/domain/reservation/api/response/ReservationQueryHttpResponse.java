package roomescape.domain.reservation.api.response;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservationtime.model.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class ReservationQueryHttpResponse {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeDto time;

    @Builder
    private ReservationQueryHttpResponse(
            final Long id,
            final String name,
            final LocalDate date,
            final ReservationTimeDto time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static List<ReservationQueryHttpResponse> from(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationQueryHttpResponse::from)
                .toList();
    }

    private static ReservationQueryHttpResponse from(final Reservation reservation) {
        return ReservationQueryHttpResponse.builder()
                .id(reservation.getId())
                .name(reservation.getName().getValue())
                .date(reservation.getDate().getValue())
                .time(ReservationTimeDto.from(reservation.getTime()))
                .build();
    }

    @Getter
    private static class ReservationTimeDto {

        private final LocalTime startAt;

        public ReservationTimeDto(final LocalTime startAt) {
            this.startAt = startAt;
        }

        public static ReservationTimeDto from(final ReservationTime time) {
            return new ReservationTimeDto(time.getStartAt());
        }
    }
}
