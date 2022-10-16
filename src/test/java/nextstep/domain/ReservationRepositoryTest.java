package nextstep.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static nextstep.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReservationRepositoryTest extends RepositoryTest {
    @Autowired
    private ReservationRepository reservations;

    @BeforeEach
    void setUp() {
        initReservationTable();
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        // given
        Reservation reservation = new Reservation(DATE, TIME, NAME);

        // when
        Reservation savedReservation = reservations.save(reservation);

        // then
        assertThat(savedReservation.getId()).isNotNull();
        assertThat(savedReservation).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(reservation);
    }

    @Test
    @DisplayName("날짜에 해당하는 예약내역을 조회한다.")
    void findAllByDate() {
        // given
        Reservation reservation = reservations.save(new Reservation(DATE, TIME, NAME));

        // when
        List<Reservation> findReservations = reservations.findAllByDate(DATE);

        // then
        assertThat(findReservations).hasSize(1);
        assertThat(findReservations.get(0)).usingRecursiveComparison()
                .isEqualTo(reservation);
    }

    @Test
    @DisplayName("날짜와 시간에 해당하는 예약내역을 삭제한다.")
    void deleteByDateAndTime() {
        // given
        Reservation reservation = reservations.save(new Reservation(DATE, TIME, NAME));

        // when, then
        assertDoesNotThrow(() -> reservations.deleteByDateAndTime(DATE, TIME));
        assertThat(reservations.findAllByDate(DATE)).isEmpty();
    }

    @Test
    @DisplayName("날짜와 시간에 해당하는 예약내역이 있으면 true, 없다면 false를 반환한다.")
    void existsByDateAndTime() {
        // given, when
        reservations.save(new Reservation(DATE, TIME, NAME));

        // then
        assertThat(reservations.existsByDateAndTime(DATE, TIME)).isTrue();
        assertThat(reservations.existsByDateAndTime(DATE, OTHER_TIME)).isFalse();
        assertThat(reservations.existsByDateAndTime(OTHER_DATE, TIME)).isFalse();
        assertThat(reservations.existsByDateAndTime(OTHER_DATE, OTHER_TIME)).isFalse();
    }
}
