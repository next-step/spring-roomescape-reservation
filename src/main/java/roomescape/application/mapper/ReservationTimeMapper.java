package roomescape.application.mapper;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimes;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;
import roomescape.repository.entity.ReservationTimeEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class ReservationTimeMapper {

    private ReservationTimeMapper() {
        throw new UnsupportedOperationException(ReservationTimeMapper.class.getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static ReservationTime toReservationTime(ReservationTimeEntity reservationTimeEntity) {
        return new ReservationTime(
                new ReservationTimeId(reservationTimeEntity.getId()),
                new ReservationTimeStartAt(reservationTimeEntity.getStartAt())
        );
    }

    public static ReservationTimes toReservationTimes(List<ReservationTimeEntity> reservationTimeEntities) {
        return new ReservationTimes(
                reservationTimeEntities.stream()
                        .map(ReservationTimeMapper::toReservationTime)
                        .collect(Collectors.toList())
        );
    }
}
