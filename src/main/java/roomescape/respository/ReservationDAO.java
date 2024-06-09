package roomescape.respository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNumber) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("time_start_at")
        );
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_date"),
                resultSet.getString("reservation_name"),
                resultSet.getLong("time_id"),
                reservationTime
        );
    };


    public Reservation insertReservation(Reservation reservation) {
        String sql = "insert into reservation (date, name, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getDate());
            ps.setString(2, reservation.getName());
            ps.setLong(3, reservation.getTimeId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return findReservationById(id);
    }

    public Reservation findReservationById(Long id) {
        String sql = "SELECT " +
                "r.id as reservation_id, " +
                "r.name as reservation_name, " +
                "r.date as reservation_date, " +
                "t.id as time_id, " +
                "t.start_at as time_start_at " +
                "FROM reservation as r " +
                "INNER JOIN reservation_time as t ON r.time_id = t.id " +
                "WHERE r.id = ?";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Reservation> readReservations() {
        String sql = "select id, date, name, time_id from reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteReservation(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
