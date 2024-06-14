package roomescape.application.mapper;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.Reservations;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.repository.entity.ReservationEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class ReservationMapper {

    private ReservationMapper() {
        throw new UnsupportedOperationException(ReservationMapper.class.getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static Reservation toReservation(ReservationEntity reservationEntity) {
        return new Reservation(
                new ReservationId(reservationEntity.getId()),
                new ReservationName(reservationEntity.getReservationName()),
                new ReservationDate(reservationEntity.getReservationDate()),
                new ReservationTimeId(reservationEntity.getReservationTimeId())
        );
    }

    public static Reservations toReservations(List<ReservationEntity> reservationEntities) {
        return new Reservations(
                reservationEntities.stream()
                        .map(ReservationMapper::toReservation)
                        .collect(Collectors.toList())
        );
    }
}
