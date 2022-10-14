package nextstep.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservations;

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        // given
        LocalDate date = LocalDate.of(2022, 12, 1);
        LocalTime time = LocalTime.of(12, 1);
        Reservation reservation = new Reservation(date, time, "조아라");

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
        LocalDate date = LocalDate.of(2022, 12, 2);
        LocalTime time = LocalTime.of(12, 2);
        Reservation reservation = reservations.save(new Reservation(date, time, "조아라"));

        // when
        List<Reservation> findReservations = reservations.findAllByDate(date);

        // then
        assertThat(findReservations).hasSize(1);
        assertThat(findReservations.get(0)).usingRecursiveComparison()
                .isEqualTo(reservation);
    }

    @Test
    @DisplayName("날짜와 시간에 해당하는 예약내역을 삭제한다.")
    void deleteByDateAndTime() {
        // given
        LocalDate date = LocalDate.of(2022, 12, 3);
        LocalTime time = LocalTime.of(12, 3);
        Reservation reservation = reservations.save(new Reservation(date, time, "조아라"));

        // when, then
        assertDoesNotThrow(() -> reservations.deleteByDateAndTime(date, time));
        assertThat(reservations.findAllByDate(date)).isEmpty();
    }

    @Test
    @DisplayName("날짜와 시간에 해당하는 예약내역이 있으면 true, 없다면 false를 반환한다.")
    void existsByDateAndTime() {
        // given
        LocalDate equalDate = LocalDate.of(2022, 12, 4);
        LocalTime equalTime = LocalTime.of(12, 4);
        LocalDate otherDate = LocalDate.of(2022, 12, 5);
        LocalTime otherTime = LocalTime.of(12, 5);

        // when
        reservations.save(new Reservation(equalDate, equalTime, "조아라"));

        // then
        assertThat(reservations.existsByDateAndTime(equalDate, equalTime)).isTrue();
        assertThat(reservations.existsByDateAndTime(equalDate, otherTime)).isFalse();
        assertThat(reservations.existsByDateAndTime(otherDate, equalTime)).isFalse();
        assertThat(reservations.existsByDateAndTime(otherDate, otherTime)).isFalse();
    }
}
