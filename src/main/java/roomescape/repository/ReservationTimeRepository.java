package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, new ReservationTimeRowMapper());
    }

    private static class ReservationTimeRowMapper implements RowMapper<ReservationTime> {
        @Override
        public ReservationTime mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReservationTime reservationTime = new ReservationTime();
            reservationTime.setId(rs.getLong("id"));
            reservationTime.setStartAt(rs.getString("start_at"));
            return reservationTime;
        }
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ReservationTimeRowMapper());
    }

    public int save(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        return jdbcTemplate.update(sql, reservationTime.getStartAt());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
