package nextstep.infra.store;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
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
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name")
        );
        return reservation;
    };

    @Override
    public Long create(Reservation reservation) {
        final String query = "INSERT INTO reservations(date, time, name) VALUES (?, ?, ?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void removeByDateAndTime(String date, String time) {
        final String query = "DELETE FROM reservations WHERE date =? AND time = ?";
        jdbcTemplate.update(query, date, time);
    }

    @Override
    public List<Reservation> findAllByDate(String date) {
        final String query = "SELECT * FROM reservations WHERE date = ?";
        return jdbcTemplate.query(query, rowMapper, date);
    }

    @Override
    public Optional<Reservation> findByDateAndTime(String date, String time) {
        final String query = "SELECT * FROM reservations WHERE date = ? AND time = ?";

        try {
            var reservation = jdbcTemplate.queryForObject(query, rowMapper, date, time);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
