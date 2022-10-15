package nextstep.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nextstep.exception.ReservationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    private Reservations reservations;

    @BeforeEach
    void setUp() {
        reservations = new Reservations(new ArrayList<>());
        reservations.make("2022-08-11", "13:00", "dani");
    }

    @DisplayName("예약을 생성한다.")
    @Test
    void make_success() {
        // given
        // when
        Integer lastReservationId = reservations.make("2022-08-11", "14:00", "dani");

        // then
        assertThat(lastReservationId).isEqualTo(2);
    }

    @DisplayName("예약을 생성할 때, `날짜`와 `시간`이 똑같은 예약이 있으면 예약을 생성할 수 없다.")
    @Test
    void make_fail() {
        // given
        // when
        // then
        assertThatThrownBy(() -> reservations.make("2022-08-11", "13:00", "daisy"))
            .isInstanceOf(ReservationException.class)
            .hasMessage("2022-08-11 13:00은 이미 예약되었습니다.");
    }

    @DisplayName("예약을 취소한다.")
    @Test
    void cancel_success() {
        // given
        // when
        reservations.cancel("2022-08-11", "13:00");

        // then
        assertThat(reservations.count()).isZero();
    }

    @DisplayName("예약 목록을 조회한다.")
    @Test
    void check_success() {
        // given
        reservations.make("2022-08-11", "14:00", "dani");

        List<Reservation> expected = List.of(
            new Reservation(LocalDate.of(2022, 8, 11), LocalTime.of(13, 0), "dani"),
            new Reservation(LocalDate.of(2022, 8, 11), LocalTime.of(14, 0), "dani")
        );

        // when
        List<Reservation> checkedReservations = reservations.check("2022-08-11");

        // then
        assertThat(checkedReservations).hasSize(2);
        assertThat(checkedReservations)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
