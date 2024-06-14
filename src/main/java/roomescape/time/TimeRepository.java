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
    private static final String TABLE_NAME = "reservation_time";
    private static final String COLUMN_START_AT = "start_at";
    private static final String COLUMN_ID = "id";

    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public TimeEntity addReservationTime(TimeEntity reservationTime) {
        String sql = String.format("insert into %s (%s) values(?)", TABLE_NAME, COLUMN_START_AT);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{COLUMN_ID});

            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);
        
        long generatedId = keyHolder.getKey().longValue();
        reservationTime.setId(generatedId);
        
        return reservationTime;
    }

    public List<TimeEntity> reservationTimes() {
        String sql = String.format("select %s, %s from %s", COLUMN_ID, COLUMN_START_AT, TABLE_NAME);
        return jdbcTemplate.query(sql, rowMapper);
    }

    private final RowMapper<TimeEntity> rowMapper = (resultSet, rowNum) ->
            new TimeEntity(
                    resultSet.getLong(COLUMN_ID),
                    resultSet.getString(COLUMN_START_AT)
            );

    public void delete(Long id) {
        String sql = String.format("delete from %s where %s = ?", TABLE_NAME, COLUMN_ID);
        jdbcTemplate.update(sql, id);
    }

    public TimeEntity time(Long timeId) {
        String sql = String.format("select %s, %s from %s where id = ?", COLUMN_ID, COLUMN_START_AT, TABLE_NAME);
        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum)  -> new TimeEntity(
                    resultSet.getLong(COLUMN_ID),
                    resultSet.getString(COLUMN_START_AT)
            ), timeId);
    }
}
