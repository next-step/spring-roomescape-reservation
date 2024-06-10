package roomescape.api.reservation;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;

@Repository
public class ReservationRepository {

  private final JdbcTemplate jdbcTemplate;

  public ReservationRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Reservation> findAll() {
    return jdbcTemplate.query("select id, name, date, time from reservations",
        ((rs, rownum) -> new Reservation(rs.getLong("id"), rs.getString("name"),
            rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime())));
  }

  public Reservation getById(Long id) {
    return jdbcTemplate.queryForObject("select id, name, date, time from reservations where id=?",
        Reservation.class, id);
  }
}
