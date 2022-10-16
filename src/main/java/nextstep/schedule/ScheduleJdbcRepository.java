package nextstep.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ScheduleJdbcRepository {
    Logger logger = LoggerFactory.getLogger(ScheduleJdbcRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Schedule> rowMapper = ((rs, rowNum) -> new Schedule(
            rs.getLong("id"),
            rs.getLong("theme_id"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime()
    ));

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

    public boolean existsByThemeIdAndDateAndTime(long themeId, LocalDate date, LocalTime time) {
        String sql = "SELECT * FROM schedule WHERE theme_id = ? AND date = ? AND time = ? LIMIT 1";
        List<Schedule> schedules = jdbcTemplate.query(sql, rowMapper, themeId, date, time);
        return !(schedules.isEmpty());
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, LocalDate date) {
        String sql = "SELECT * FROM schedule WHERE theme_id = ? AND date = ?";
        return jdbcTemplate.query(sql, rowMapper, themeId, date);
    }

    public boolean deleteSchedule(Long scheduleId) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        int deletedCount = jdbcTemplate.update(sql, scheduleId);
        return deletedCount > 0;
    }

    public void clear() {
        String sql = "DELETE FROM schedule";
        jdbcTemplate.update(sql);
        logger.info(sql);
    }
}
