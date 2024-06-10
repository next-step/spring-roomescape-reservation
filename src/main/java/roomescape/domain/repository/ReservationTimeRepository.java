package roomescape.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;


@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Time> reservationTimeRowMapper;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation_time")
            .usingGeneratedKeyColumns("id");
        this.reservationTimeRowMapper = (resultSet, rowNum) -> Time.createReservationTime(
            resultSet.getLong("id"),
            resultSet.getString("start_at")
        );
    }

    public long save(Time time) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(time);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public List<Time> findAll() {
        final String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    public Optional<Time> findById(long id) {
        final String sql = "select id, start_at from reservation_time where id = ?";
        try {
            Time time = jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, id);
            return Optional.of(time);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(long id) {
        final String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
