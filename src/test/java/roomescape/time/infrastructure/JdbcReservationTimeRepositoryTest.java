package roomescape.time.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import roomescape.time.domain.ReservationTime;

@JdbcTest
class JdbcReservationTimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private JdbcReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate, dataSource);
        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");

        jdbcTemplate.execute("INSERT INTO reservation_time(start_at) VALUES ('15:40')");
    }

    @Test
    @DisplayName("예약 시간을 저장한다.")
    void testSaveReservationTime() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.parse("10:00"));

        // when
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);

        // then
        assertThat(savedReservationTime.getStartAt()).isEqualTo(reservationTime.getStartAt());
    }

    @Test
    @DisplayName("예약 시간 단건 조회를 한다.")
    void testFindById() {
        ReservationTime reservationTime = reservationTimeRepository.findById(1L).get();

        assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.parse("15:40"));
    }

    @Test
    @DisplayName("모든 예약 시간을 조회한다.")
    void testFindAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        assertThat(reservationTimes).hasSize(1);
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void testDelete() {
        reservationTimeRepository.delete(1L);

        assertThat(reservationTimeRepository.findById(1L)).isEmpty();
    }
}
