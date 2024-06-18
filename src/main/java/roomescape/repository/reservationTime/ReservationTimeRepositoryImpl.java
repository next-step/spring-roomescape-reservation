package roomescape.repository.reservationTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> {
        return new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at")
        );
    };

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation_time", rowMapper);
    }

    @Override
    public ReservationTime findByTimeId(String timeId) {
        return jdbcTemplate.queryForObject("SELECT * FROM reservation_time where id = ?", rowMapper, timeId);
    }

    @Override
    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation_time(start_at) values(?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, reservationTime.getStartAt());
            return pstmt;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        reservationTime.setId(key);
        return reservationTime;
    }

    @Override
    public void deleteReservationTime(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}
