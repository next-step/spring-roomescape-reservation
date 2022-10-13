package nextstep.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

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
    void insert() {
        Reservation reservation = new Reservation(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(2, 0),
            "최현구"
        );

        Reservation saved = reservationDao.insert(reservation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(reservation);
    }
}
