package nextstep.infrastructure;

import java.util.Optional;
import nextstep.domain.Schedule;
import nextstep.domain.repository.ScheduleRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDao implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Schedule schedule) {
        jdbcTemplate.update(
            "insert into schedule (themeId, reservationId) values (?, ?)",
            schedule.getThemeId(),
            schedule.getReservationId()
        );
    }

    @Override
    public Optional<Schedule> findBy(Long reservationId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select id, themeId, reservationId from schedule where reservationId = ?",
                (rs, rowNum) -> new Schedule(
                    rs.getLong("id"),
                    rs.getLong("themeId"),
                    rs.getLong("reservationId")
                ),
                reservationId
            ));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Schedule> findBy(Long themeId, Long reservationId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select id, themeId, reservationId from schedule where themeId = ? and reservationId = ?",
                (rs, rowNum) -> new Schedule(
                    rs.getLong("id"),
                    rs.getLong("themeId"),
                    rs.getLong("reservationId")
                ),
                themeId,
                reservationId
            ));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from schedule");
    }
}
