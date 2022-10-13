package nextstep;

import org.springframework.context.annotation.Primary;
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
        return false;
    }

    @Override
    public Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        return Optional.empty();
    }

    @Override
    public List<Reservation> findByDate(LocalDate date) {
        String sql = "SELECT * FROM reservation WHERE date = ?";
        return jdbcTemplate.query(
                sql,
                ((rs, count) -> new Reservation(
                        rs.getLong("id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(),
                        rs.getString("name"))
                ),
                date
        );
    }

    @Override
    public boolean deleteByDateAndTime(LocalDate date, LocalTime time) {
        return false;
    }

    @Override
    public void clear() {

    }
}
