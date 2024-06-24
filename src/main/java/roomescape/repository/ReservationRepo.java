package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationRepo {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Reservation mapReservation(ResultSet rs, int rowNum) throws SQLException {
        Long reservationId = rs.getLong("reservation_id");
        String reservationName = rs.getString("reservation_name");
        LocalDate reservationDate = LocalDate.parse(rs.getString("reservation_date"));

        // 예약 시간
        Long timeId = rs.getLong("time_id");
        String startAt = rs.getString("start_at");
        ReservationTime time = new ReservationTime(timeId, startAt);

        // 테마
        Long themeId = rs.getLong("theme_id");
        String themeName = rs.getString("theme_name");
        String themeDescription = rs.getString("theme_description");
        String themeThumbnail = rs.getString("theme_thumbnail");
        Theme theme = new Theme(themeId, themeName, themeDescription, themeThumbnail);

        return new Reservation(reservationId, reservationName, reservationDate, time, theme);
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT r.id as reservation_id, r.name as reservation_name, r.date as reservation_date,
                t.id as time_id, t.start_at as start_at,
                th.id as theme_id, th.name as theme_name, th.description as theme_description, th.thumbnail as theme_thumbnail
                FROM reservation as r
                LEFT JOIN reservation_time as t ON r.time_id = t.id
                LEFT JOIN theme as th ON r.theme_id = th.id
                """;

        return jdbcTemplate.query(sql, this::mapReservation);
    }

    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id, theme_id) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, String.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            ps.setLong(4, reservation.getTheme().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public boolean existsByDateAndTimeId(LocalDate date, Long timeId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, timeId);
        return count != null && count > 0;
    }
}
