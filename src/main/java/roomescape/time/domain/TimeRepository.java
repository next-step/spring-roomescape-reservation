package roomescape.time.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class TimeRepository {

    private static final String SAVE_SQL = "insert into reservation_time (start_at) values (?)";
    private static final String FIND_BY_ID_SQL = "select * from reservation_time where id = ?";
    private static final String FIND_ALL_SQL = "select * from reservation_time";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_START_AT = "start_at";
    private static final int INDEX_ONE = 1;

    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, new String[]{COLUMN_ID});
            preparedStatement.setString(INDEX_ONE, time.getStartAt());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Time findById(Long timeId) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, (rs, rowNum) -> new Time(rs.getLong(COLUMN_ID), rs.getString(COLUMN_START_AT)), timeId);
    }

    public List<Time> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, (rs, rowNum) -> new Time(rs.getLong(COLUMN_ID), rs.getString(COLUMN_START_AT)));
    }
}
