package nextstep;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("날짜와 시간이 모두 일치하는지 알 수 있다.")
    @Test
    void equalsDateTime() {
        Reservation reservation = new Reservation(
            LocalDate.of(2022, 10, 5),
            LocalTime.of(13, 0),
            "최현구"
        );

        assertThat(reservation.equalsDateTime(
            LocalDate.of(2022, 10, 5),
            LocalTime.of(13, 0)
        )).isTrue();
        assertThat(reservation.equalsDateTime(
            LocalDate.of(2022, 10, 5),
            LocalTime.of(13, 1)
        )).isFalse();
    }
}
