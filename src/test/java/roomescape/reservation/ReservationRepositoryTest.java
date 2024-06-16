package roomescape.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimePolicy;
import roomescape.reservationTime.ReservationTimeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
class ReservationRepositoryTest {

    private ReservationRepository reservationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    ReservationTimePolicy reservationTimePolicy = new ReservationTimePolicy();
    ReservationPolicy reservationPolicy = new ReservationPolicy();

    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate, reservationPolicy, reservationTimePolicy);
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate, reservationTimePolicy);
        reservationTimePolicy = new ReservationTimePolicy();
        reservationPolicy = new ReservationPolicy();
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("CREATE TABLE reservation_time (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "start_at VARCHAR(255) NOT NULL, " +
                "PRIMARY KEY (id))");

        jdbcTemplate.execute("CREATE TABLE reservation (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "date VARCHAR(255) NOT NULL, " +
                "time_id BIGINT, " +   // 컬럼 수정
                "PRIMARY KEY (id))");  // 외래키 제거
    }

    @DisplayName("예약 전체 조회")
    @Test
    void findAll() {
        // given
        final ReservationTime requestTime = new ReservationTime("15:40", reservationTimePolicy);
        final Long id = reservationTimeRepository.save(requestTime);
        final ReservationTime savedTime = reservationTimeRepository.findById(id);

        final Reservation reservation1 = new Reservation("제이슨", "2024-08-05", savedTime, reservationPolicy);
        final Reservation reservation2 = new Reservation("심슨", "2024-08-05", savedTime, reservationPolicy);

        List<Object[]> reservations = Arrays.asList(reservation1, reservation2).stream()
                .map(reservation -> new Object[]{
                        reservation.getName(), reservation.getDate(), reservation.getReservationTime().getId()
                })
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO reservation(name, date, time_id) VALUES (?,?,?)", reservations);

        // when
        List<Reservation> actual = reservationRepository.findAll();

        // then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("예약 저장")
    @Test
    void save() {
        // given
        final ReservationTime requestTime = new ReservationTime("15:40", reservationTimePolicy);
        final Long id = reservationTimeRepository.save(requestTime);
        final ReservationTime savedTime = reservationTimeRepository.findById(id);

        final Reservation request = new Reservation("테스트", "2024-08-05", savedTime, reservationPolicy);

        // when
        Long savedReservationId = reservationRepository.save(request);

        // then
        Reservation actual = reservationRepository.findById(savedReservationId);
        assertThat(actual.getDate()).isEqualTo(request.getDate());
        assertThat(actual.getName()).isEqualTo(request.getName());
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteById() {
        // given
        final ReservationTime requestTime = new ReservationTime("15:40", reservationTimePolicy);
        final Long id = reservationTimeRepository.save(requestTime);
        final ReservationTime savedTime = reservationTimeRepository.findById(id);

        final Reservation request = new Reservation("테스트", "2024-08-05", savedTime, reservationPolicy);
        Long savedReservationId = reservationRepository.save(request);

        // when
        reservationRepository.deleteById(savedReservationId);

        // then
        assertThatThrownBy(() -> reservationRepository.findById(savedReservationId))
                .isInstanceOf(DataAccessException.class);


    }
}
