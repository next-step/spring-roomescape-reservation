package roomescape.support;

import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservation.ui.dto.ReservationRequest;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.reservationtime.ui.dto.ReservationTimeRequest;
import roomescape.apply.theme.domain.Theme;
import roomescape.apply.theme.ui.dto.ThemeRequest;

public class ReservationsFixture {

    private ReservationsFixture() {

    }

    public static ReservationTime reservationTime() {
        return reservationTime("10:00");
    }

    public static ReservationTime reservationTime(String time) {
        return ReservationTime.of(time);
    }

    public static Theme theme(String name, String description) {
        return Theme.of(name,
                description,
                "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");
    }


    public static Theme theme() {
        return theme("레벨2 탈출", "우테코 레벨2를 탈출하는 내용입니다.");
    }

    public static Reservation reservation(ReservationTime time, Theme theme) {
        return Reservation.of("테스트_예약자", "2099-01-01", time, theme);
    }

    public static ReservationTimeRequest reservationTimeRequest() {
        return new ReservationTimeRequest("10:00");
    }

    public static ThemeRequest themeRequest() {
        return new ThemeRequest("레벨2 탈출",
                "우테코 레벨2를 탈출하는 내용입니다.",
                "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");
    }

    public static ReservationRequest reservationRequest(long timeId, long themeId) {
        return new ReservationRequest("테스터", "2099-01-12", timeId, themeId);
    }
}
