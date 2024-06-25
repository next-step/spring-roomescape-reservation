package roomescape.domain.time;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ReservationTimeRowMapper rowMapper = new ReservationTimeRowMapper();

  public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<ReservationTime> findAll() {
    return jdbcTemplate.query("select id, start_at from reservation_time", rowMapper);
  }

  public ReservationTime findById(long id) {
    return jdbcTemplate.queryForObject("select id, start_at from reservation_time where id=?",
        ReservationTime.class, id);
  }

  public void deleteById(long id) {
    jdbcTemplate.update("delete from reservation_time where id=?", id);
  }

  public ReservationTime save(CreateReservationTime newReservationTime) {
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement statement = con.prepareStatement(
          "insert into reservation_time (start_at) values (?)", new String[]{"id"});
      statement.setTime(1, Time.valueOf(newReservationTime.startAt()));

      return statement;
    }, generatedKeyHolder);
    return new ReservationTime(generatedKeyHolder.getKeyAs(Long.class),
        newReservationTime.startAt());
  }
}
