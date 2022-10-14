package nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertReservation;
    private final RowMapper<Reservation> mapper;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertReservation = new SimpleJdbcInsert(dataSource)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
        this.mapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name")
        );
    }

    public Reservation save(Reservation reservation) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
        Long id = insertReservation.executeAndReturnKey(parameters).longValue();
        return new Reservation(id, reservation);
    }

    public List<Reservation> findAllByDate(LocalDate date) {
        String sql = "SELECT * FROM reservation WHERE date = ?";
        return jdbcTemplate.query(sql, mapper, date);
    }

    public int deleteByDateTime(LocalDate date, LocalTime time) {
        String sql = "DELETE FROM reservation WHERE date = ? AND time = ?";
        return jdbcTemplate.update(sql, date, time);
    }

    public boolean existsByDateTime(LocalDate date, LocalTime time) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        return count > 0;
    }
}
