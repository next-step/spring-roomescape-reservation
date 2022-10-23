package nextstep.infra.store;

import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ScheduleJdbcRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Schedule> rowMapper = (resultSet, rowNum) -> {
        Schedule schedule = new Schedule(
            resultSet.getLong("id"),
            resultSet.getLong("theme_id"),
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

    @Override
    public Optional<Schedule> findById(Long id) {
        final String query = "SELECT * FROM schedules WHERE id = ?";

        try {
            var theme = jdbcTemplate.queryForObject(query, rowMapper, id);
            return Optional.ofNullable(theme);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Schedule> findAllByDate(String date) {
        final String query = "SELECT * FROM schedules WHERE date = ?";

        return jdbcTemplate.query(query, rowMapper, date);
    }

    @Override
    public List<Schedule> findAllByThemeId(Long themeId) {
        final String query = "SELECT * FROM schedules WHERE theme_id = ?";

        return jdbcTemplate.query(query, rowMapper, themeId);
    }

    @Override
    public List<Schedule> findAllByThemeIdAndDate(Long themeId, String date) {
        final String query = "SELECT * FROM schedules WHERE theme_id = ? AND date = ?";

        return jdbcTemplate.query(query, rowMapper, themeId, date);
    }

    @Override
    public void removeById(Long id) {
        final String query = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
