package roomescape.reservationTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ReservationTime> rowMapper;
    private final ReservationTimePolicy reservationTimePolicy;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate, ReservationTimePolicy reservationTimePolicy) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at"),
                reservationTimePolicy

        );
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        this.reservationTimePolicy = reservationTimePolicy;
    }

    public List<ReservationTime> findAll() {
        final String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Long save(ReservationTime reservationTime) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationTime);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public void deleteById(Long id) {
        final String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }

    public boolean existsById(Long id) {
        final String sql = "select exists(select 1 from reservation_time where id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public ReservationTime findById(Long id) {
        final String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
