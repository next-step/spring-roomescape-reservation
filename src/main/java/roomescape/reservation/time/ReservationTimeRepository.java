package roomescape.reservation.time;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "reservation_time";

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTimeEntity addReservationTime(ReservationTimeEntity reservationTime) {
        String sql = String.format("insert into %s (start_At) values(?)", TABLE_NAME);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{"id"});

            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);
        
        long generatedId = keyHolder.getKey().longValue();
        reservationTime.setId(generatedId);
        
        return reservationTime;
    }

    public List<ReservationTimeEntity> reservationTimes() {
        String sql = String.format("select id, start_At from %s", TABLE_NAME);
        return jdbcTemplate.query(sql, rowMapper);
    }

    private final RowMapper<ReservationTimeEntity> rowMapper = (resultSet, rowNum) ->
            new ReservationTimeEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("start_At")
            );

    public void delete(Long id) {
        String sql = String.format("delete from %s where id = ?", TABLE_NAME);
        jdbcTemplate.update(sql, id);
    }
}
