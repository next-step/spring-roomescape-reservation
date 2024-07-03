package roomescape.respository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.*;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNumber) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("time_start_at")
        );
        Theme theme = new Theme(
                resultSet.getLong("theme_id"),
                resultSet.getString("theme_name")
        );
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_date"),
                resultSet.getString("reservation_name"),
                reservationTime,
                theme
        );
    };

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationCreateResponseDto insertReservation(ReservationCreateDto reservationCreateDto, Long timeId,
                                                          Long themeId) {
        String sql = "insert into reservation (date, name, time_id, theme_id) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservationCreateDto.getDate());
            ps.setString(2, reservationCreateDto.getName());
            ps.setLong(3, timeId);
            ps.setLong(4, themeId);
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new ReservationCreateResponseDto(id);

    }

    public List<Reservation> readReservations() {
        String sql = """
                SELECT 
                    r.id as reservation_id, 
                    r.name as reservation_name, 
                    r.date as reservation_date, 
                    t.id as time_id, 
                    t.start_at as time_start_at, 
                    th.id as theme_id,
                    th.name as theme_name
                FROM reservation as r 
                INNER JOIN reservation_time as t ON r.time_id = t.id
                INNER JOIN theme as th ON r.theme_id = th.id
                """;

        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteReservation(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }

    public boolean isReservationExistByTimeId(Long id) {
        String sql = "select count(*) from reservation where time_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public boolean isReservationExistByTimeIdAndThemeIdAndDate(Long timeId, Long themeId, String date) {
        String sql = "select count(*) from reservation where time_id = ? and theme_id = ? and date = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{timeId, themeId, date}, Integer.class);
        return count != null && count > 0;
    }
}
