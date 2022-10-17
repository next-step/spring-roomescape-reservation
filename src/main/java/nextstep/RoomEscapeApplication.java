package nextstep;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class RoomEscapeApplication implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    public RoomEscapeApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(RoomEscapeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("""
            CREATE TABLE reservation (
                name VARCHAR(255),
                reservation_date DATE,
                reservation_time TIME,
                CONSTRAINT reservation_date_time_unique UNIQUE (reservation_date, reservation_time)
            )"""
        );
    }
}
