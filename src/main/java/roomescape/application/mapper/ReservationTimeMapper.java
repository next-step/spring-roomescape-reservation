package roomescape.application.mapper;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;
import roomescape.repository.entity.ReservationTimeEntity;

public abstract class ReservationTimeMapper {

    public static ReservationTime toReservationTime(ReservationTimeEntity reservationTimeEntity) {
        return new ReservationTime(
                new ReservationTimeId(reservationTimeEntity.getId()),
                new ReservationTimeStartAt(reservationTimeEntity.getStartAt())
        );
    }
}
