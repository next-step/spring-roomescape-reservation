package nextstep.theme;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThemeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clear() {
        jdbcTemplate.update("DELETE FROM THEME");
    }
}
