package nextstep.infra.h2;

import nextstep.core.schedule.Schedule;
import nextstep.core.schedule.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ScheduleH2Repository implements ScheduleRepository {
    private static final RowMapper<Schedule> ROW_MAPPER = (resultSet, rowNum) -> new Schedule(
            resultSet.getLong("id"),
            resultSet.getLong("theme_id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime()
    );
    private final JdbcTemplate template;

    public ScheduleH2Repository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Schedule save(Schedule schedule) {
        String query = "INSERT INTO schedules(theme_id, date, time) VALUES (?, ?, ?)";
        template.update(query, schedule.getThemeId(), schedule.getDate(), schedule.getTime());

        Long id = template.queryForObject("SELECT last_insert_id()", Long.class);
        return new Schedule(id, schedule.getThemeId(), schedule.getDate(), schedule.getTime());
    }

    @Override
    public List<Schedule> findByThemeIdAndDate(Long themeId, LocalDate date) {
        String query = "SELECT * FROM schedules WHERE theme_id = ? AND date = ?";
        return template.query(query, ROW_MAPPER, themeId, date);
    }
}
