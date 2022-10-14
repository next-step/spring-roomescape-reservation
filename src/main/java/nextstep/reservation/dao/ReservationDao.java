package nextstep.reservation.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reservation.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsReservation(LocalDate date, LocalTime time) {
        String sql = "SELECT count(*) FROM reservations WHERE date=? and time=?";
        return 0 < jdbcTemplate.queryForObject(sql, Integer.class, date, time);
    }

    public Long insert(LocalDate date, LocalTime time, String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservations(date, time, name) VALUES(?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setObject(1, date);
            preparedStatement.setObject(2, time);
            preparedStatement.setString(3, name);
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findReservations(LocalDate date) {
        String sql = "SELECT * FROM reservations WHERE date=?";
        List<Reservation> customers = jdbcTemplate.query(sql,
                                                         (rs, rowNum) -> new Reservation(
                                                                 rs.getLong("id"),
                                                                 rs.getObject("date", LocalDate.class),
                                                                 rs.getObject("time", LocalTime.class),
                                                                 rs.getString("name")
                                                         ), date);
        return customers;
    }

    public void removeReservation(LocalDate date, LocalTime time) {
        String sql = "DELETE FROM reservations WHERE date=? and time=?";
        jdbcTemplate.update(sql, date, time);
    }
}
