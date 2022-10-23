package nextstep.infra.store;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getLong("schedule_id")
        );
        return reservation;
    };

    @Override
    public Long create(Reservation reservation) {
        final String query = "INSERT INTO reservations(name, schedule_id) VALUES (?, ?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setLong(2, reservation.getScheduleId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Reservation> findByScheduleId(Long scheduleId) {
        final String query = "SELECT * FROM reservations WHERE schedule_id = ?";

        try {
            var theme = jdbcTemplate.queryForObject(query, rowMapper, scheduleId);
            return Optional.ofNullable(theme);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAllByScheduledIds(List<Long> scheduleIds) {

        String inSql = String.join(",", Collections.nCopies(scheduleIds.size(), "?"));
        String query = String.format("select * from reservations where id in (%s)", inSql);

        return jdbcTemplate.query(query, rowMapper, scheduleIds.toArray());
    }

    @Override
    public void removeByScheduleId(Long scheduleId) {
        final String query = "DELETE FROM reservations WHERE schedule_id = ?";
        jdbcTemplate.update(query, scheduleId);
    }
}
