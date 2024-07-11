package roomescape.domain.reservationtime.infrastructure;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ReservationTimeEntity {

    private final Long id;
    private final LocalTime startAt;
    private final LocalDateTime createdAt;

    @Builder
    private ReservationTimeEntity(
            final Long id,
            final LocalTime startAt,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.startAt = startAt;
        this.createdAt = createdAt;
    }

    public static ReservationTimeEntity fromModel(ReservationTime reservationTime) {
        return ReservationTimeEntity.builder()
                .id(reservationTime.getIdValue())
                .startAt(reservationTime.getStartAt())
                .createdAt(reservationTime.getCreatedAt())
                .build();
    }

    public ReservationTime toModel() {
        return ReservationTime.builder()
                .id(new ReservationTimeId(this.id))
                .startAt(this.startAt)
                .createdAt(this.createdAt)
                .build();
    }
}
