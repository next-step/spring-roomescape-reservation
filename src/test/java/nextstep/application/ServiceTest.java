package nextstep.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected void initReservationTable() {
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation("
            + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "schedule_id BIGINT NOT NULL,"
            + "date DATE NOT NULL,"
            + "time TIME NOT NULL,"
            + "name VARCHAR(100) NOT NULL"
            + ")");
    }

    protected void initThemeTable() {
        jdbcTemplate.execute("DROP TABLE theme IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE theme("
            + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "name VARCHAR(100) NOT NULL,"
            + "description TEXT NOT NULL,"
            + "price DECIMAL NOT NULL"
            + ")");
    }

    protected void initScheduleTable() {
        jdbcTemplate.execute("DROP TABLE schedule IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE schedule("
            + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "theme_id BIGINT NOT NULL,"
            + "date DATE NOT NULL,"
            + "time TIME NOT NULL"
            + ")");
    }
}
