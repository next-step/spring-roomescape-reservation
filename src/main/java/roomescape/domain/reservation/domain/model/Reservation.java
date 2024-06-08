package roomescape.domain.reservation.domain.model;

import lombok.Builder;
import lombok.Getter;
import roomescape.global.infrastructure.ClockHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Reservation {

    private Long id;
    private final ReservationGuestName name;
    private final ReservationTimeStamp timeStamp;
    private final ReservationStatus status;
    private final LocalDateTime createdAt;

    @Builder
    private Reservation(
            final ReservationGuestName name,
            final ReservationTimeStamp timeStamp,
            final ReservationStatus status,
            final LocalDateTime createdAt
    ) {
        this.name = name;
        this.timeStamp = timeStamp;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Reservation defaultOf(
            final ReservationGuestName name,
            final ReservationTimeStamp timeStamp,
            final ClockHolder clockHolder
    ) {
        return Reservation.builder()
                .name(name)
                .timeStamp(timeStamp)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(clockHolder.getCurrentSeoulTime())
                .build();
    }

    public LocalDate fetchReservationDate() {
        return this.timeStamp.getDate();
    }

    public LocalTime fetchReservationTime() {
        return this.timeStamp.getTime();
    }

    public boolean matchesId(final Long id) {
        return this.id.equals(id);
    }

}
