package nextstep.persistence.schedule;

import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleJdbcRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final RowMapper<Schedule> rowMapper;

    public ScheduleJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");
        this.rowMapper = (resultSet, rowNumber) -> new Schedule(
                resultSet.getLong("id"),
                resultSet.getLong("theme_id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime());
    }

    @Override
    public Schedule save(Schedule schedule) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("theme_id", schedule.getThemeId())
                .addValue("date", schedule.getDate())
                .addValue("time", schedule.getTime());
        Long id = insertActor.executeAndReturnKey(params).longValue();

        return new Schedule(id, schedule.getThemeId(), schedule.getDate(), schedule.getTime());
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> schedules = jdbcTemplate.query(sql, rowMapper, id);

        return Optional.ofNullable(DataAccessUtils.singleResult(schedules));
    }

    @Override
    public List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date) {
        String sql = "SELECT * FROM schedule WHERE theme_id = ? AND date = ?";

        return jdbcTemplate.query(sql, rowMapper, themeId, date);
    }

    @Override
    public Boolean existByThemId(Long themeId) {
        String sql = "SELECT EXISTS(SELECT * FROM schedule WHERE theme_id = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, themeId);

        return exists;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
