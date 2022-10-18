package nextstep.data.jdbc.repository;

import nextstep.data.jdbc.repository.support.AbstractJdbcRepository;
import nextstep.domain.schedule.domain.model.Schedule;
import nextstep.domain.schedule.domain.model.ScheduleRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ScheduleJdbcRepository extends AbstractJdbcRepository<Schedule> implements ScheduleRepository {

    protected ScheduleJdbcRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String tableName() {
        return "schedule";
    }

    @Override
    protected RowMapper<Schedule> rowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getLong("theme_id"),
                rs.getObject("date", LocalDate.class),
                rs.getObject("time", LocalTime.class)
        );
    }

    @Override
    public Schedule save(Schedule schedule) {
        long id = simpleJdbcInsert.executeAndReturnKey(
                Map.of(
                        "theme_id", schedule.themeId(),
                        "date", schedule.date(),
                        "time", schedule.time()
                )
        ).longValue();

        return schedule.withId(id);
    }

    @Override
    public List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date) {
        return jdbcTemplate.query(
                "SELECT * FROM schedule WHERE theme_id=? AND date=?",
                rowMapper(),
                themeId, date
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM schedule WHERE id=?", id);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    public Optional<Schedule> findByThemeIdAndDateTime(Long themeId, LocalDateTime dateTime) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM schedule WHERE theme_id=? AND date=? AND time=?",
                            rowMapper(),
                            themeId, dateTime.toLocalDate(), dateTime.toLocalTime()
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM schedule WHERE id=?",
                            rowMapper(),
                            id
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
