package roomescape.support;

import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservation.ui.dto.ReservationRequest;
import roomescape.apply.time.domain.ReservationTime;
import roomescape.apply.time.ui.dto.ReservationTimeRequest;

public class ReservationsFixture {

    private ReservationsFixture() {

    }

    public static ReservationTime reservationTime() {
        return reservationTime("10:00");
    }

    public static ReservationTime reservationTime(String time) {
        return ReservationTime.of(time);
    }

    public static Reservation reservation(ReservationTime time) {
        return Reservation.of("테스트_예약자", "2099-01-01", time);
    }

    public static ReservationTimeRequest reservationTimeRequest() {
        return new ReservationTimeRequest("10:00");
    }

    public static ReservationRequest reservationRequest(long timeId) {
        return new ReservationRequest("테스터", "2099-01-12", timeId);
    }
}
