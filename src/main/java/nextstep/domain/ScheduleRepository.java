package nextstep.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Schedule> mapper;

    public ScheduleRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");
        this.mapper = (resultSet, rowNum) -> new Schedule(
                resultSet.getLong("id"),
                resultSet.getLong("theme_id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    }

    public Schedule save(Schedule schedule) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(schedule);
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Schedule(id, schedule);
    }

    public List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date) {
        String sql = "select * from schedule where theme_id = ? and date = ?";
        return jdbcTemplate.query(sql, mapper, themeId, date);
    }

    public List<Long> findIdsByThemeId(Long themeId) {
        String sql = "select id from schedule where theme_id = ?";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> resultSet.getLong("id"),
                themeId);
    }

    public Schedule findById(Long id) {
        String sql = "select * from schedule where id = ?";
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    public void deleteById(Long id) {
        String sql = "delete from schedule where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time) {
        return countByThemeIdAndDateAndTime(themeId, date, time) > 0;
    }

    private int countByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time) {
        String sql = "select count(*) from schedule where theme_id = ? and date = ? and time = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, themeId, date, time);
    }

    public boolean existsById(Long id) {
        String sql = "select count(*) from schedule where id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }
}
