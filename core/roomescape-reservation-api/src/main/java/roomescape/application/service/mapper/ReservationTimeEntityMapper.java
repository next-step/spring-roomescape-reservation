package roomescape.application.service.mapper;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.repository.entity.ReservationTimeEntity;

public final class ReservationTimeEntityMapper {

    private ReservationTimeEntityMapper() {
        throw new UnsupportedOperationException(this.getClass().getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static ReservationTimeEntity toReservationTimeEntity(ReservationTime reservationTime) {
        return new ReservationTimeEntity(reservationTime.getId(), reservationTime.getStartAt());
    }
}
