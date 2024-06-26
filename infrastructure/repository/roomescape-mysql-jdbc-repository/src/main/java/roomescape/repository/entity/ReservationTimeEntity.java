package roomescape.repository.entity;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;

import java.time.LocalTime;

public class ReservationTimeEntity {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTimeEntity(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeEntity from(ReservationTime reservationTime) {
        return new ReservationTimeEntity(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public ReservationTimeEntity withId(Long id) {
        return new ReservationTimeEntity(id, this.startAt);
    }

    public ReservationTime toDomain() {
        return new ReservationTime(
                new ReservationTimeId(this.id),
                new ReservationTimeStartAt(this.startAt)
        );
    }
}
