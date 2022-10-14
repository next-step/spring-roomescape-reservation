package nextstep.infra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import nextstep.domain.Schedule;
import nextstep.domain.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDao implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertSchedule;
    private final RowMapper<Schedule> mapper;

    public ScheduleDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertSchedule = new SimpleJdbcInsert(dataSource)
            .withTableName("schedule")
            .usingGeneratedKeyColumns("id");
        this.mapper = (resultSet, rowNum) -> new Schedule(
            resultSet.getLong("id"),
            resultSet.getLong("theme_id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime()
        );
    }

    @Override
    public Schedule save(Schedule schedule) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(schedule);
        Long id = insertSchedule.executeAndReturnKey(parameters).longValue();
        return new Schedule(id, schedule);
    }

    @Override
    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date) {
        String sql = "SELECT * FROM schedule WHERE theme_id = ? AND date = ?";
        return jdbcTemplate.query(sql, mapper, themeId, date);
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time) {
        String sql = "SELECT COUNT(*) FROM schedule WHERE theme_id = ? AND date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, themeId, date, time);
        return count > 0;
    }

    @Override
    public Schedule findByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT * FROM schedule WHERE date = ? AND time = ?";
        return jdbcTemplate.queryForObject(sql, mapper, date, time);
    }
}
