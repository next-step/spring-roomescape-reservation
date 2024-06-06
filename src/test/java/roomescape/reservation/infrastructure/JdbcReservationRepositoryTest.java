package roomescape.reservation.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.repository.ReservationRepository;
import roomescape.time.domain.ReservationTime;

@JdbcTest
class JdbcReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new JdbcReservationRepository(jdbcTemplate, dataSource);
        jdbcTemplate.execute("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");

        jdbcTemplate.execute("INSERT INTO reservation_time(start_at) VALUES ('15:40')");
        jdbcTemplate.execute("INSERT INTO reservation(name, date, time_id) VALUES ('브라운', '2023-08-05', 1)");
    }

    @Test
    @DisplayName("예약을 데이터베이스에 저장한다.")
    void testSaveReservation() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.parse("15:40"));
        Reservation reservation = new Reservation("브라운", LocalDate.parse("2023-08-05"), reservationTime);

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertThat(savedReservation.getId()).isEqualTo(2L);
        assertThat(savedReservation).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(reservation);
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void testFindAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(1);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void testDeleteById() {
        reservationRepository.deleteById(1L);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reservation WHERE id = 1", Integer.class);
        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("예약이 존재하면 true를 반환한다.")
    void testExistsById_Return_True() {
        boolean result = reservationRepository.existsById(1L);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("예약이 존재하지 않으면 false를 반환한다.")
    void testExistsById_Return_False() {
        boolean result = reservationRepository.existsById(5L);

        assertThat(result).isFalse();
    }
}
