package roomescape.repository.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;


    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAllReservations() {

        return jdbcTemplate.query("SELECT * FROM reservation", (rs, rowNum) -> {
            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time")
            );
        });
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into Reservation(name, date, time) values(?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, reservation.getName());
            pstmt.setString(2, reservation.getDate());
            pstmt.setString(3, reservation.getTime());
            return pstmt;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        reservation.setId(key);

        return reservation;
    }

    @Override
    public void deleteReservation(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
