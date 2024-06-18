package roomescape.repository.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                rs.getLong("time_id"),
                rs.getString("time_start_at")
        );

        return new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("reservation_name"),
                rs.getString("reservation_date"),
                reservationTime
                );
    };

    @Override
    public List<Reservation> findAllReservations() {

        String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name as reservation_name, \n" +
                "    r.date as reservation_date, \n" +
                "    t.id as time_id, \n" +
                "    t.start_at as time_start_at \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t \n" +
                "on r.time_id = t.id";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation createReservation(Reservation reservation, ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into reservation(name, date, time_id) values(?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, reservation.getName());
            pstmt.setString(2, reservation.getDate());
            pstmt.setLong(3, reservationTime.getId());
            return pstmt;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        reservation.setId(key);
        reservation.setTime(reservationTime);

        return reservation;
    }

    @Override
    public void deleteReservation(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
