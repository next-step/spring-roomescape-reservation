package nextstep.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {
    @Test
    @DisplayName("날짜가 같으면 true, 아니라면 false를 반환한다.")
    void equalsDate() {
        // given
        LocalDate equalDate = LocalDate.of(2022, 12, 4);
        LocalDate otherDate = LocalDate.of(2022, 12, 5);

        // when
        Reservation reservation = new Reservation(
                equalDate,
                LocalTime.of(12, 4),
                "조아라");

        // then
        assertThat(reservation.equalsDate(equalDate)).isTrue();
        assertThat(reservation.equalsDate(otherDate)).isFalse();
    }

    @Test
    @DisplayName("날짜와 시간이 모두 같으면 true, 아니라면 false를 반환한다.")
    void equalsDateAndTime() {
        // given
        LocalDate equalDate = LocalDate.of(2022, 12, 4);
        LocalTime equalTime = LocalTime.of(12, 4);
        LocalDate otherDate = LocalDate.of(2022, 12, 5);
        LocalTime otherTime = LocalTime.of(12, 5);

        // when
        Reservation reservation = new Reservation(
                equalDate,
                equalTime,
                "조아라");

        // then
        assertThat(reservation.equalsDateAndTime(equalDate, equalTime)).isTrue();
        assertThat(reservation.equalsDateAndTime(equalDate, otherTime)).isFalse();
        assertThat(reservation.equalsDateAndTime(otherDate, equalTime)).isFalse();
        assertThat(reservation.equalsDateAndTime(otherDate, otherTime)).isFalse();
    }
}
