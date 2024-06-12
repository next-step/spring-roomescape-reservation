package roomescape.application.mapper;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.repository.entity.ReservationTimeEntity;

public abstract class ReservationTimeEntityMapper {

    public static ReservationTimeEntity toReservationTimeEntity(ReservationTime reservationTime) {
        return new ReservationTimeEntity(reservationTime.getId(), reservationTime.getStartAt());
    }
}
