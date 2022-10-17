package nextstep.schedule.persistence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import nextstep.schedule.domain.Schedule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDao {

    private static final RowMapper<Schedule> scheduleMapper =
        (resultSet, rowNum) -> new Schedule(
            resultSet.getLong("id"),
            resultSet.getLong("theme_id"),
            LocalDate.parse(resultSet.getString("date")),
            LocalTime.parse(resultSet.getString("time"))
        );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScheduleDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Schedule schedule) {
        String query = "INSERT INTO schedule (theme_id, date, time) VALUES (:themeId, :date, :time)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(schedule);
        jdbcTemplate.update(query, sqlParameterSource, keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }


    public List<Schedule> findBy(Long themeId, LocalDate date) {
        String query = "SELECT * FROM schedule WHERE (theme_id = :themeId AND date = :date)";
        return jdbcTemplate.query(query, Map.of("themeId", themeId, "date", date), scheduleMapper);
    }

    public void deleteById(Long id) {
        String query = "DELETE FROM schedule WHERE (id = :id)";
        jdbcTemplate.update(query, Map.of("id", id));
    }
}
