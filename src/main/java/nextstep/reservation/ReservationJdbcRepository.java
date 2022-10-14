package nextstep.reservation;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class ReservationJdbcRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> rowMapper = ((rs, count) -> new Reservation(
            rs.getLong("id"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime(),
            rs.getString("name"))
    );

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(Reservation reservation) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime())
                .addValue("name", reservation.getName());
        reservation.setId((Long) simpleJdbcInsert.executeAndReturnKey(source));
        return reservation;
    }

    @Override
    public boolean existsReservation(LocalDate date, LocalTime time) {
        return this.findByDateAndTime(date, time).isPresent();
    }

    @Override
    public Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT * FROM reservation WHERE date = ? AND time = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, date, time));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findByDate(LocalDate date) {
        String sql = "SELECT * FROM reservation WHERE date = ?";
        return jdbcTemplate.query(sql, rowMapper, date);
    }

    @Override
    public boolean deleteByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "DELETE FROM reservation WHERE date = ? AND time = ?";
        int deleteCount = jdbcTemplate.update(sql, date, time);
        return deleteCount > 0;
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.update(sql);
    }
}
