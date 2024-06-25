package roomescape.application.service.mapper;

import roomescape.domain.reservation.ReservationView;
import roomescape.domain.reservation.ReservationViews;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.repository.projection.ReservationViewProjection;

import java.util.List;
import java.util.stream.Collectors;

public final class ReservationViewMapper {

    private ReservationViewMapper() {
        throw new UnsupportedOperationException(ReservationViewMapper.class.getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static ReservationView toReservationView(ReservationViewProjection reservationViewProjection) {
        return new ReservationView(
                new ReservationId(reservationViewProjection.getReservationId()),
                new ReservationName(reservationViewProjection.getReservationName()),
                new ReservationDate(reservationViewProjection.getReservationDate()),
                new ReservationTimeId(reservationViewProjection.getReservationTimeId()),
                new ReservationTimeStartAt(reservationViewProjection.getReservationTimeStartAt()),
                new ThemeId(reservationViewProjection.getThemeId()),
                new ThemeName(reservationViewProjection.getThemeName())
        );
    }

    public static ReservationViews toReservationViews(List<ReservationViewProjection> reservationViewProjections) {
        return new ReservationViews(
                reservationViewProjections.stream()
                        .map(ReservationViewMapper::toReservationView)
                        .collect(Collectors.toList())
        );
    }
}
