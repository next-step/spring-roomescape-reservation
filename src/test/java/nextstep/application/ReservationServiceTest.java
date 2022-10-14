package nextstep.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation("
            + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "date DATE NOT NULL,"
            + "time TIME NOT NULL,"
            + "name VARCHAR(100) NOT NULL"
            + ")");
    }

    @DisplayName("날짜와 시간에 삭제하려는 예약이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void reservationDeleteException() {
        assertThatThrownBy(() -> reservationService.removeByDateTime(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(1, 0)
        )).isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("시간과 날짜에 해당하는 예약정보가 없습니다.");
    }
}
