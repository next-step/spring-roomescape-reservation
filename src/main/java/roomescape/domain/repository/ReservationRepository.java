package roomescape.domain.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;


@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(Reservation reservation) {
        String sql = "insert into reservation(name, date, time_id) values(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);
        return Reservation.createReservation(Objects.requireNonNull(keyHolder.getKey()).longValue(),
            reservation.getName(),
            reservation.getDate(), reservation.getTime().getId(), reservation.getTime().getStartAt());
    }


    public List<Reservation> findAll() {
        String sql = "SELECT \n"
            + "    r.id as reservation_id, \n"
            + "    r.name as reservation_name, \n"
            + "    r.date as reservation_date, \n"
            + "    t.id as time_id, \n"
            + "    t.start_at as time_start_at \n"
            + "FROM reservation as r \n"
            + "inner join reservation_time as t \n"
            + "on r.time_id = t.id";

        return jdbcTemplate.query(
            sql, (rs, rowNum) -> {
                return Reservation.createReservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getLong("time_id"),
                    rs.getString("time_start_at"));
            });
    }

    public int deleteById(long id) {
        final String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
