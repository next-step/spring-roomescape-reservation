package roomescape.application.mapper;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.Reservations;
import roomescape.domain.reservation.vo.ReservationDateTime;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.repository.entity.ReservationEntity;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ReservationMapper {

    // domain import

    public static Reservation toReservation(ReservationEntity reservationEntity) {
        return new Reservation(
                new ReservationId(reservationEntity.getId()),
                new ReservationName(reservationEntity.getReservationName()),
                new ReservationDateTime(reservationEntity.getReservationDateTime())
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
