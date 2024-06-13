package roomescape.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Entity.Reservation;
import roomescape.Entity.ReservationTime;
import roomescape.Entity.Theme;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("time_start_at")
        );
        Theme theme = new Theme(
                resultSet.getLong("theme_id"),
                resultSet.getString("theme_name"),
                resultSet.getString("theme_description"),
                resultSet.getString("theme_thumbnail")
        );
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_name"),
                resultSet.getString("reservation_date"),
                reservationTime,
                theme
        );
    };

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT " +
                "r.id as reservation_id, " +
                "r.name as reservation_name, " +
                "r.date as reservation_date, " +
                "t.id as time_id, " +
                "t.start_at as time_start_at, " +
                "th.id as theme_id, " +
                "th.name as theme_name, " +
                "th.description as theme_description, " +
                "th.thumbnail as theme_thumbnail " +
                "FROM reservation as r " +
                "INNER JOIN reservation_time as t " +
                "ON r.time_id = t.id " +
                "INNER JOIN theme as th " +
                "ON r.theme_id = th.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation findById(Long id) {
        String sql = "SELECT " +
                "r.id as reservation_id, " +
                "r.name as reservation_name, " +
                "r.date as reservation_date, " +
                "t.id as time_id, " +
                "t.start_at as time_start_at, " +
                "th.id as theme_id, " +
                "th.name as theme_name, " +
                "th.description as theme_description, " +
                "th.thumbnail as theme_thumbnail " +
                "FROM reservation as r " +
                "INNER JOIN reservation_time as t " +
                "ON r.time_id = t.id " +
                "INNER JOIN theme as th " +
                "ON r.theme_id = th.id " +
                "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public long save(String name, String date, Long timeId, Long themeId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            String sql = "insert into reservation (name, date, time_id, theme_id) values (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setLong(3, timeId);
            ps.setLong(4, themeId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public long deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
