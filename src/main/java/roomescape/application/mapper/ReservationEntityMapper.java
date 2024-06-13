package roomescape.application.mapper;

import roomescape.domain.reservation.Reservation;
import roomescape.repository.entity.ReservationEntity;

public abstract class ReservationEntityMapper {

    // qpplication ㄱㄷ

    //  db: memberJpaReposi implements exteneds jpa
    // db : jd

    // <- db: memory imple memberRepository
    //  <- db: repository implements exteneds jpa : gradel jpa

    public static ReservationEntity toReservationEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(),
                reservation.getReservationName(),
                reservation.getReservationDateTime()
        );
    }
}
