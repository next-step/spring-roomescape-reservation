package nextstep.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ScheduleRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");
    }

    public Schedule save(Schedule schedule) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(schedule);
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Schedule(id, schedule);
    }

    public List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date) {
        String sql = "select * from schedule where theme_id = ? and date = ?";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Schedule(
                        resultSet.getLong("id"),
                        resultSet.getLong("theme_id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime()
                ),
                themeId, date);
    }

    public void deleteById(Long id) {
        String sql = "delete from schedule where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
