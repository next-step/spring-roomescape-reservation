package nextstep.data.jdbc.repository;

import nextstep.data.jdbc.repository.support.AbstractJdbcRepository;
import nextstep.domain.reservation.domain.model.Reservation;
import nextstep.domain.reservation.domain.model.ReservationRepository;
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
public class ReservationJdbcRepository extends AbstractJdbcRepository<Reservation> implements ReservationRepository {

    protected ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String tableName() {
        return "reservation";
    }

    @Override
    protected RowMapper<Reservation> rowMapper() {
        return (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getLong("schedule_id"),
                rs.getObject("date", LocalDate.class),
                rs.getObject("time", LocalTime.class),
                rs.getString("name")
        );
    }

    @Override
    public Reservation save(Reservation reservation) {
        long id = simpleJdbcInsert.executeAndReturnKey(
                Map.of(
                        "schedule_id", reservation.scheduleId(),
                        "date", reservation.date(),
                        "time", reservation.time(),
                        "name", reservation.name()
                )
        ).longValue();

        return reservation.withId(id);
    }

    @Override
    public List<Reservation> findAllByDate(LocalDate date) {
        return jdbcTemplate.query(
                "SELECT * FROM reservation WHERE date=?",
                rowMapper(),
                date
        );
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    public void deleteByScheduleIdAndDateAndTime(Long scheduleId, LocalDate date, LocalTime time) {
        jdbcTemplate.update("DELETE FROM reservation WHERE schedule_id =? AND date=? AND time=?", scheduleId, date, time);
    }

    @Override
    public Optional<Reservation> findByScheduleIdAndDateTime(Long scheduleId, LocalDateTime dateTime) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM reservation WHERE schedule_id=? AND date=? AND time=?",
                            rowMapper(),
                            scheduleId, dateTime.toLocalDate(), dateTime.toLocalTime()
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
