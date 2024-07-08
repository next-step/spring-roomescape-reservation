package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTimeRq;
import roomescape.model.ReservationTime;

import java.sql.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ReservationTimeRepo {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private ReservationTime mapReservationTime(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        LocalTime startAt = rs.getTime("start_at").toLocalTime();
        return new ReservationTime(id, startAt);
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, this::mapReservationTime);
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        PreparedStatementSetter pss = ps -> ps.setLong(1, id);
        List<ReservationTime> results = jdbcTemplate.query(sql, pss, this::mapReservationTime);

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
            String startAt = reservationTimeRq.getStartAt().toString();
            ps.setString(1, startAt);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean existsByStartAt(LocalTime startAt) {
        String sql = "SELECT COUNT(*) FROM reservation_time WHERE start_at = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, startAt);
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
