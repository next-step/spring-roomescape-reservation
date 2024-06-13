package roomescape.application.mapper;

import roomescape.domain.reservation.Reservation;
import roomescape.repository.entity.ReservationEntity;

public abstract class ReservationEntityMapper {

    public static ReservationEntity toReservationEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(),
                reservation.getReservationName(),
                reservation.getReservationDate(),
                reservation.getReservationTimeId()
        );
    }
}
