package roomescape.infra.time;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.Collection;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.CreateReservationTime;
import roomescape.domain.time.ReservationTime;

@Repository
class ReservationTimeJdbcRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ReservationTimeRowMapper rowMapper;
  private final NamedParameterJdbcTemplate namedTemplate;

  public ReservationTimeJdbcRepository(JdbcTemplate jdbcTemplate,
      ReservationTimeRowMapper rowMapper, NamedParameterJdbcTemplate namedTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.rowMapper = rowMapper;
    this.namedTemplate = namedTemplate;
  }

  public boolean isUsed(ReservationTime time) {
    return jdbcTemplate.queryForObject("select count(id) from reservation where time_id=? limit 1",
        (rs, i) -> rs.getLong(1) > 0, time.getId());
  }

  public List<ReservationTimeEntity> findAll() {
    return jdbcTemplate.query("select id, start_at from reservation_time", rowMapper);
  }

  public List<ReservationTimeEntity> findByIds(Collection<Long> ids) {
    SqlParameterSource parameterSource = new MapSqlParameterSource("ids", ids);
    return namedTemplate.query("select id, start_at from reservation_time where id in (:ids)",
        parameterSource, rowMapper);
  }

  public ReservationTimeEntity findById(long id) {
    ReservationTimeEntity reservationTime = null;
    try {
      reservationTime = jdbcTemplate.queryForObject(
          "select id, start_at from reservation_time where id=?", rowMapper, id);
    } catch (EmptyResultDataAccessException ignored) {
    }
    return reservationTime;
  }

  public void deleteById(long id) {
    jdbcTemplate.update("delete from reservation_time where id=?", id);
  }

  public long save(CreateReservationTime newReservationTime) {
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement statement = con.prepareStatement(
          "insert into reservation_time (start_at) values (?)", new String[]{"id"});
      statement.setTime(1, Time.valueOf(newReservationTime.startAt()));

      return statement;
    }, generatedKeyHolder);
    return generatedKeyHolder.getKeyAs(Long.class);
  }
}
