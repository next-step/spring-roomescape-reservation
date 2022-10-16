package nextstep.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ScheduleJdbcRepository {
    Logger logger = LoggerFactory.getLogger(ScheduleJdbcRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ScheduleJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");
    }

    public Schedule save(Schedule schedule) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("theme_id", schedule.getThemeId())
                .addValue("date", schedule.getDate())
                .addValue("time", schedule.getTime());
        Long id = (Long) simpleJdbcInsert.executeAndReturnKey(source);
        schedule.setId(id);
        return schedule;
    }

    public void clear() {
        String sql = "DELETE FROM schedule";
        jdbcTemplate.update(sql);
        logger.info(sql);
    }
}
