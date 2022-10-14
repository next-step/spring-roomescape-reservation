package nextstep.infra;

import java.time.LocalDate;
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
    public List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date) {
        String sql = "SELECT * FROM schedule WHERE theme_id = ? AND date = ?";
        return jdbcTemplate.query(sql, mapper, themeId, date);
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
