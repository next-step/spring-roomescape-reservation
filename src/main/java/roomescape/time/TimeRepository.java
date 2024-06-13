package roomescape.time;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "reservation_time";

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public TimeEntity addReservationTime(TimeEntity reservationTime) {
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

    public List<TimeEntity> reservationTimes() {
        String sql = String.format("select id, start_At from %s", TABLE_NAME);
        return jdbcTemplate.query(sql, rowMapper);
    }

    private final RowMapper<TimeEntity> rowMapper = (resultSet, rowNum) ->
            new TimeEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("start_At")
            );

    public void delete(Long id) {
        String sql = String.format("delete from %s where id = ?", TABLE_NAME);
        jdbcTemplate.update(sql, id);
    }
}
