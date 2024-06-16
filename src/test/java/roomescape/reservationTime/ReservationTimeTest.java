package roomescape.reservationTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTimeTest {

    ReservationTimePolicy reservationTimePolicy = new ReservationTimePolicy();

    @DisplayName("예약 시간 형태가 적절하지 못하면 예외가 발생한다")
    @Test
    void invalidTime() {
        // given
        final String invalidTime = "15:4";

        // when, then
        assertThatIllegalArgumentException().isThrownBy(() -> new ReservationTime(invalidTime, reservationTimePolicy));
        assertThatThrownBy(() -> new ReservationTime(invalidTime, reservationTimePolicy))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간 형식이 올바르지 않습니다.");
    }

    @DisplayName("예약 시간이 null 이면 예외가 발생한다.")
    @Test
    void invalidTime2() {
        // given
        final String invalidTime = null;

        // when, then
        assertThatNullPointerException().isThrownBy(() -> new ReservationTime(invalidTime, reservationTimePolicy));
    }

}
