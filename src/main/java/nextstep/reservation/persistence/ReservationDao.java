package nextstep.reservation.persistence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import nextstep.reservation.domain.Reservation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao implements ReservationStorage {

    private static final RowMapper<Reservation> reservationMapper =
        (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getLong("schedule_id"),
            LocalDate.parse(resultSet.getString("date")),
            LocalTime.parse(resultSet.getString("time")),
            resultSet.getString("name")
        );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReservationDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long insert(Reservation reservation) {
        String query = "INSERT INTO reservation (schedule_id, date, time, name) VALUES (:scheduleId, :date, :time, :name)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reservation);
        jdbcTemplate.update(query, sqlParameterSource, keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public List<Reservation> findBy(Long scheduleId, LocalDate date) {
        String query = "SELECT * FROM reservation WHERE (schedule_id = :scheduleId AND date = :date)";
        return jdbcTemplate.query(query, Map.of("scheduleId", scheduleId, "date", date), reservationMapper);
    }

    @Override
    public Optional<Reservation> findBy(Long scheduleId, LocalDate date, LocalTime time) {
        String query = "SELECT * FROM reservation WHERE (schedule_id = :scheduleId AND date = :date AND time = :time)";
        return jdbcTemplate.query(
                query,
                Map.of("scheduleId", scheduleId, "date", date, "time", time),
                reservationMapper
            )
            .stream()
            .findAny();
    }

    @Override
    public void deleteBy(Long scheduleId, LocalDate date, LocalTime time) {
        String query = "DELETE FROM reservation WHERE (schedule_id = :scheduleId AND date = :date AND time = :time)";
        jdbcTemplate.update(query, Map.of("scheduleId", scheduleId, "date", date, "time", time));
    }
}
