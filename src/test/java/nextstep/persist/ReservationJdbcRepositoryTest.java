package nextstep.persist;

import nextstep.domain.reservation.model.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ReservationJdbcRepositoryTest {
    private final ReservationJdbcRepository sut;

    @Autowired
    public ReservationJdbcRepositoryTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.sut = new ReservationJdbcRepository(jdbcTemplate, dataSource);
    }

    @DisplayName("예약 save")
    @Test
    void save() {
        // given
        LocalDate date = LocalDate.of(2022, 8, 11);
        LocalTime time = LocalTime.of(11, 10);
        String name = "신지혜";
        Reservation reservation = new Reservation(date, time, name);

        // when
        Reservation actual = sut.save(reservation);

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @DisplayName("예약 exist 확인 - date, time 조건")
    @Test
    void existByDateTime() {
        // given
        LocalDate existDate = LocalDate.of(2022, 8, 11);
        LocalTime existTime = LocalTime.of(11, 10);
        Reservation existReservation = new Reservation(existDate, existTime, "신지혜");
        sut.save(existReservation);

        // when
        Boolean actual = sut.existByDateTime(existDate, existTime);

        // then
        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("예약 목록 조회 - date 조건")
    @Test
    void findAllByDate() {
        // given
        LocalDate existDate = LocalDate.of(2022, 8, 11);
        Reservation reservation1 = new Reservation(1L, existDate, LocalTime.of(11, 10), "신지혜");
        Reservation reservation2 = new Reservation(2L, existDate, LocalTime.of(12, 10), "지혜신");
        sut.save(reservation1);
        sut.save(reservation2);

        // when
        List<Reservation> actual = sut.findAllByDate(existDate);

        // then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("예약 삭제 - date, time 조건")
    @Test
    void deleteByDateTime() {
        // given
        LocalDate existDate = LocalDate.of(2022, 8, 11);
        LocalTime existTime = LocalTime.of(11, 10);
        Reservation existReservation = new Reservation(existDate, existTime, "신지혜");
        sut.save(existReservation);

        // when
        sut.deleteByDateTime(existDate, existTime);

        // then
        assertThat(sut.existByDateTime(existDate, existTime)).isEqualTo(false);
    }
}