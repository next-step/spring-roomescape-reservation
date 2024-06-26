package roomescape.application.service.mapper;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.theme.Theme;
import roomescape.repository.entity.ReservationEntity;

public final class ReservationMapper {

    private ReservationMapper() {
        throw new UnsupportedOperationException(this.getClass().getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static Reservation toReservation(
            ReservationEntity reservationEntity,
            ReservationTime reservationTime,
            Theme theme
    ) {
        return new Reservation(
                new ReservationId(reservationEntity.getId()),
                new ReservationName(reservationEntity.getReservationName()),
                new ReservationDate(reservationEntity.getReservationDate()),
                reservationTime,
                theme
        );
    }
}
