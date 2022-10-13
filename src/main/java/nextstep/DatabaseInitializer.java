package nextstep;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Profile("!test")
public class DatabaseInitializer implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            String sql = "INSERT INTO reservation(`date`, `time`, name) VALUES(?, ?, ?)";
            jdbcTemplate.update(sql, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"), "박민영");
        } catch (Exception e) {
            throw new RuntimeException("DB 초기 데이터를 생성하는데 실패했습니다.", e);
        }
    }
}
