package roomescape.reservation.infrastructure;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.repository.ReservationRepository;
import roomescape.time.domain.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        reservation.setId(id);
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT r.id, r.name, r.date, t.id as time_id, t.start_at FROM reservation as r INNER JOIN reservation_time as t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getString("start_at")
                )
        ));
    }

    @Override
    public void deleteById(Long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, reservationId);
    }

    @Override
    public boolean existsById(Long reservationId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, reservationId);
        return count != null && count.equals(1);
    }
}
