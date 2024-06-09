package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation insertReservation(Reservation reservation) {
        String sql = "insert into reservation (date, name, time) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getDate());
            ps.setString(2, reservation.getName());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return findReservationById(id);
    }

    public Reservation findReservationById(Long id) {
        String sql = "select id, date, name, time from reservation where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNumber) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("date"),
                            resultSet.getString("name"),
                            resultSet.getString("time")
                    );
                    return reservation;
                }, id);
    }
}
