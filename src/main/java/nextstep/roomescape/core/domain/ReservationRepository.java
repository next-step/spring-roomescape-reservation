package nextstep.roomescape.core.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReservationRepository {

    private static final RowMapper<Reservation> ROW_MAPPER = (rs, rowNum) -> new Reservation(
        rs.getInt("id"),
        rs.getInt("scheduleId"),
        rs.getString("name")
    );

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        final String sql = "SELECT * FROM reservations";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void delete(Integer id) {
        final String sql = "DELETE FROM reservations WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int add(Reservation reservation) {
        final String sql = "INSERT INTO reservations(scheduleId, name) VALUES (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, reservation.scheduleId());
            ps.setString(2, reservation.name());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Boolean containsBySchedule(Integer scheduleId) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM reservations WHERE scheduleId = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, scheduleId);
    }

    public Boolean existsById(Integer id) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM reservations WHERE id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }
}
