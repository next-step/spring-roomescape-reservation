package nextstep.roomescape.core;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public class Reservations {

    private static final RowMapper<Reservation> ROW_MAPPER = (rs, rowNum) -> new Reservation(
        rs.getInt("id"),
        rs.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
        rs.getTime("time").toInstant().atZone(ZoneId.systemDefault()).toLocalTime(),
        rs.getString("name")
    );

    private final JdbcTemplate jdbcTemplate;

    public Reservations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllByDate(LocalDate date) {
        final String sql = "SELECT * FROM reservations WHERE date = ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, date);
    }

    public void deleteByDateAndTime(LocalDate date, LocalTime time) {
        if (!existsByDateAndTime(date, time)) {
            throw new IllegalArgumentException("해당 날짜, 시간에 예약 정보가 존재하지 않습니다.");
        }
        final String sql = "DELETE FROM reservations WHERE date = ? AND time = ?";
        jdbcTemplate.update(sql, date, time);
    }

    public int register(LocalDate date, LocalTime time, String name) {
        if (existsByDateAndTime(date, time)) {
            throw new IllegalArgumentException("해당 날짜, 시간에 예약 정보가 이미 존재합니다.");
        }
        final String sql = "INSERT INTO reservations(date, time, name) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ps.setString(3, name);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    private Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM reservations WHERE date = ? AND time =?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, date, time);
    }
}
