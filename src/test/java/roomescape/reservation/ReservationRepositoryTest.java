package roomescape.reservation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class ReservationRepositoryTest {
    private ReservationRepository reservationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation(" +
                "id BIGINT AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "date VARCHAR(255), " +
                "time VARCHAR(255), " +
                "PRIMARY KEY (id))");
    }

    @DisplayName("예약 전체 조회")
    @Test
    void findAll() {
        // given
        final Reservation reservation1 = new Reservation("제이슨", "2023-08-05", "15:40");
        final Reservation reservation2 = new Reservation("심슨", "2023-08-05", "15:40");
        List<Object[]> reservations = Arrays.asList(reservation1, reservation2).stream()
                .map(reservation -> new Object[]{reservation.getName(), reservation.getDate(), reservation.getTime()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO reservation(name, date, time) VALUES (?,?,?)", reservations);

        // when
        List<Reservation> actual = reservationRepository.findAll();

        // then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("예약 저장")
    @Test
    void save() {
        // given
        final Reservation request = new Reservation("테스트", "2023-08-05", "15:40");

        // when
        Long savedReservaionId = reservationRepository.save(request);

        // then
        Reservation actual = reservationRepository.findById(savedReservaionId);
        assertThat(actual.getDate()).isEqualTo(request.getDate());
        assertThat(actual.getName()).isEqualTo(request.getName());
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteById() {
        // given
        final Reservation request = new Reservation("테스트", "2023-08-05", "15:40");
        Long savedRequestId = reservationRepository.save(request);
        Reservation savedRequest = reservationRepository.findById(savedRequestId);
        assertThat(savedRequest.getName()).isEqualTo(request.getName());

        // when
        reservationRepository.deleteById(savedRequestId);

        // then
        assertThatThrownBy(() -> reservationRepository.findById(savedRequestId))
                .isInstanceOf(DataAccessException.class);


    }
}