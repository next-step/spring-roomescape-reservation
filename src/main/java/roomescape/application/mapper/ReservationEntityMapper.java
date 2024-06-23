package roomescape.application.mapper;

import roomescape.domain.reservation.Reservation;
import roomescape.repository.entity.ReservationEntity;

public final class ReservationEntityMapper {

    private ReservationEntityMapper() {
        throw new UnsupportedOperationException(ReservationEntityMapper.class.getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static ReservationEntity toReservationEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTimeId(),
                reservation.getThemeId()
        );
    }
}
