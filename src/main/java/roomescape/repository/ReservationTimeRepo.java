package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTimeRq;
import roomescape.model.ReservationTime;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationTimeRepo {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private ReservationTime mapReservationTime(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String startAt = rs.getString("start_at");
        return new ReservationTime(id, startAt);
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, this::mapReservationTime);
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        List<ReservationTime> results = jdbcTemplate.query(sql, new Object[]{id}, this::mapReservationTime);

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    public Long save(ReservationTimeRq reservationTimeRq) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservationTimeRq.getStartAt());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
