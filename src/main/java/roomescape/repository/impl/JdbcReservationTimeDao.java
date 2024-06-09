package roomescape.repository.impl;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@Repository
public class JdbcReservationTimeDao implements ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        final String sql = "INSERT INTO reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);

        return reservationTime.toEntity(reservationTime, keyHolder.getKey().longValue());
    }

    @Override
    public List<ReservationTime> findAll() {
        final String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id")
                        , rs.getString("start_at")
                )
        );
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        final String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        ReservationTime reservationTime = null;
        try {
            reservationTime = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new ReservationTime(
                            rs.getLong("id")
                            , rs.getString("start_at"))
                    , id);
        } catch (EmptyResultDataAccessException e) {
        }

        return Optional.ofNullable(reservationTime);
    }

    @Override
    public Long findByStartAt(String startAt) {
        final String sql = "SELECT count(*) FROM reservation_time WHERE start_at = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, startAt);
    }
}
