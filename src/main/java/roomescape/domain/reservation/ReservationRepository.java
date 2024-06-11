package roomescape.domain.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.CreateReservation;
import roomescape.domain.reservation.Reservation;

@Repository
public class ReservationRepository {

  private final JdbcTemplate jdbcTemplate;

  public ReservationRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Reservation> findAll() {
    return jdbcTemplate.query("select id, name, date, time from reservation",
        (rs, rownum) -> {
          System.out.println("rs = " + rs);
          return new Reservation(rs.getLong("id"), rs.getString("name"),
              rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime());
        });
  }

  public Reservation getById(Long id) {
    return jdbcTemplate.queryForObject("select id, name, date, time from reservation where id=?",
        (rs, rownum) -> new Reservation(rs.getLong("id"), rs.getString("name"),
            rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime()), id);
  }

  public Reservation save(CreateReservation newReservation) {
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    int updatedRow = jdbcTemplate.update(con -> {
      PreparedStatement statement = con.prepareStatement(
          "insert into reservation (name, date, time) values ( ?, ?, ? )", new String[]{"id"});
      statement.setString(1, newReservation.name());
      statement.setDate(2, Date.valueOf(newReservation.date()));
      statement.setTime(3, Time.valueOf(newReservation.time()));
      return statement;
    }, generatedKeyHolder);
    return new Reservation(generatedKeyHolder.getKeyAs(Long.class), newReservation.name(),
        newReservation.date(), newReservation.time());
  }

  public void delete(long id) {
    jdbcTemplate.update("delete from reservation where id = ?", id);
  }


}
