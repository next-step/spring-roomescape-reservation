package nextstep.roomescape.core.domain;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepository {

    private static final RowMapper<Schedule> ROW_MAPPER = (rs, rowNum) -> new Schedule(
        rs.getInt("id"),
        rs.getInt("themeId"),
        rs.getDate("date").toLocalDate(),
        rs.getTime("time").toLocalTime()
    );

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Schedule> findAll() {
        final String sql = "SELECT * FROM schedules";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void deleteById(Integer id) {
        final String sql = "DELETE FROM schedules WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int add(Schedule schedule) {
        final String sql = "INSERT INTO schedules(themeId, date, time) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, schedule.themeId());
            ps.setDate(2, Date.valueOf(schedule.date()));
            ps.setTime(3, Time.valueOf(schedule.time()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Boolean exists(Integer themeId, LocalDate date, LocalTime time) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM schedules WHERE themeId = ? AND date = ? AND time = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, themeId, date, time);
    }

    public Boolean existsById(Integer id) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM schedules WHERE id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public Boolean existsAliveByThemeId(Integer themeId) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM schedules WHERE themeId = ? AND date > now() -1)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, themeId);
    }
}
