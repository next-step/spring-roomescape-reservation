package nextstep.infra.store;

import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.Objects;

@Repository
public class ScheduleJdbcRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Schedule> rowMapper = (resultSet, rowNum) -> {
        Schedule schedule = new Schedule(
            resultSet.getLong("id"),
            resultSet.getLong("themeId"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime()
        );
        return schedule;
    };

    @Override
    public Long create(Schedule schedule) {
        final String query = "INSERT INTO schedules(theme_id, date, time) VALUES (?, ?, ?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, schedule.getThemeId());
            ps.setDate(2, Date.valueOf(schedule.getDate()));
            ps.setTime(3, Time.valueOf(schedule.getTime()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
