package nextstep.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation("
            + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "date DATE NOT NULL,"
            + "time TIME NOT NULL,"
            + "name VARCHAR(100)"
            + ")");
    }

    @DisplayName("예약 저장")
    @Test
    void save() {
        Reservation reservation = new Reservation(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(2, 0),
            "최현구"
        );

        Reservation saved = reservationRepository.save(reservation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(reservation);
    }

    @DisplayName("날짜에 해당하는 모든 예약 조회")
    @Test
    void findAllByDate() {
        Reservation 최현구_10월1일_예약 = saveReservation(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(1, 0),
            "최현구"
        );
        Reservation 최현팔_10월1일_예약 = saveReservation(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(5, 0),
            "최현팔"
        );
        Reservation 최현칠_9월5일_예약 = saveReservation(
            LocalDate.of(2022, 9, 5),
            LocalTime.of(1, 0),
            "최현칠"
        );

        List<Reservation> reservations = reservationRepository.findAllByDate(LocalDate.of(2022, 10, 1));

        assertThat(reservations).containsExactly(최현구_10월1일_예약, 최현팔_10월1일_예약);
    }

    @DisplayName("날짜에 해당하는 모든 예약 조회할 때 저장되어 있는 예약이 없어도 빈 값이 조회된다.")
    @Test
    void findAllByDateEmpty() {
        List<Reservation> reservations = reservationRepository.findAllByDate(LocalDate.of(2022, 10, 1));

        assertThat(reservations).isEmpty();
    }

    @DisplayName("날짜와 시간에 해당되는 예약 제거")
    @Test
    void deleteByDateTime() {
        saveReservation(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(1, 0),
            "최현구"
        );

        int count = reservationRepository.deleteByDateTime(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(1, 0)
        );

        assertThat(count).isEqualTo(1);
        assertThat(reservationRepository.findAllByDate(LocalDate.of(2022, 10, 1))).isEmpty();
    }

    @DisplayName("날짜와 시간에 해당되는 예약이 있는지 확인")
    @Test
    void existsByDateTime() {
        saveReservation(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(1, 0),
            "최현구"
        );

        boolean result = reservationRepository.existsByDateTime(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(1, 0)
        );

        assertThat(result).isTrue();
    }

    private Reservation saveReservation(LocalDate date, LocalTime time, String name) {
        return reservationRepository.save(new Reservation(date, time, name));
    }
}
