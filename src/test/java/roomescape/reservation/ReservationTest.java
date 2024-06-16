package roomescape.reservation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimePolicy;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    final ReservationPolicy reservationPolicy = new ReservationPolicy();
    final ReservationTimePolicy reservationTimePolicy = new ReservationTimePolicy();

    @DisplayName("예약 생성 시 이름이 특수문자를 포함하고 있으면 예외가 발생한다.")
    @Test
    void createReservation() {
        // given
        final String name = "!@";
        final String date = "2024-07-01";
        final ReservationTime reservationTime = new ReservationTime(1L, "10:00", reservationTimePolicy);

        // when, then
        assertThatIllegalArgumentException().isThrownBy(() -> new Reservation(name, date, reservationTime, reservationPolicy));

    }

}
