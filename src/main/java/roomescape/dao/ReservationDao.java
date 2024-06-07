package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;
import roomescape.dto.ReservationRequest;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("reservation_id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            new ReservationTime(resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime()),
            new Theme(resultSet.getLong("theme_id"),
                    resultSet.getString("theme_name"),
                    resultSet.getString("theme_description"),
                    resultSet.getString("theme_thumbnail")));

    public Long create(ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name, date, time_id, theme_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setDate(2, Date.valueOf(reservationRequest.date()));
            ps.setLong(3, reservationRequest.timeId());
            ps.setLong(4, reservationRequest.themeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Reservation read(long reservationId) {
        String sql = "SELECT r.id AS reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id AS time_id, " +
                "t.start_at AS time_value, " +
                "th.id AS theme_id, " +
                "th.name AS theme_name, " +
                "th.description AS theme_description, " +
                "th.thumbnail AS theme_thumbnail " +
                "FROM reservation AS r " +
                "INNER JOIN reservation_time AS t " +
                "ON r.time_id = t.id " +
                "INNER JOIN theme AS th " +
                "ON r.theme_id = th.id " +
                "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, reservationId);
    }

    public List<Reservation> readAll() {
        String sql = "SELECT r.id AS reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id AS time_id, " +
                "t.start_at AS time_value, " +
                "th.id AS theme_id, " +
                "th.name AS theme_name, " +
                "th.description AS theme_description, " +
                "th.thumbnail AS theme_thumbnail " +
                "FROM reservation AS r " +
                "INNER JOIN reservation_time AS t " +
                "ON r.time_id = t.id " +
                "INNER JOIN theme AS th " +
                "ON r.theme_id = th.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, reservationId);
    }

    public boolean isDuplicate(ReservationRequest reservationRequest) {
        String sql = "SELECT count(1) FROM reservation WHERE date = ? AND time_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, Date.valueOf(reservationRequest.date()), reservationRequest.timeId()) > 0;
    }
}
