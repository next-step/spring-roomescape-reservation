package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.model.ReservationTime;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper rowMapper = (resultSet, rowNum) -> new ReservationTime(
        resultSet.getLong("id"),
        resultSet.getString("start_at")
    );

    @Autowired
    public ReservationTimeRepository(JdbcTemplate jdbcTemplate, SimpleJdbcInsert jdbcInsert, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime save(String startAt) {
        Map<String, Object> params = new HashMap<>();
        params.put("startAt", startAt);
        return new ReservationTime(jdbcInsert.executeAndReturnKey(params).longValue(), startAt);
    }

    public List<ReservationTime> readAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
