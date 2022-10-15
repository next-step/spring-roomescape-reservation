package nextstep.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation("2022-08-11", "13:00", "dani");
    }

    @DisplayName("예약 날짜와 시간을 비교한다.")
    @ParameterizedTest
    @MethodSource("isSameWithDateAndTimeArguments")
    void isSameWithDateAndTime(String date, String time, boolean expected) {
        // given
        // when
        // then
        assertThat(reservation.isSame(date, time)).isEqualTo(expected);
    }

    private static Stream<Arguments> isSameWithDateAndTimeArguments() {
        return Stream.of(
            Arguments.of("2022-08-11", "13:00", true),
            Arguments.of("2022-08-11", "14:00", false)
        );
    }

    @DisplayName("예약 날짜를 비교한다.")
    @ParameterizedTest
    @MethodSource("isSameWithDateArguments")
    void isSameWithDate(String date, boolean expected) {
        // given
        // when
        // then
        assertThat(reservation.isSame(date)).isEqualTo(expected);
    }

    private static Stream<Arguments> isSameWithDateArguments() {
        return Stream.of(
            Arguments.of("2022-08-11", true),
            Arguments.of("2022-08-12", false)
        );
    }
}
