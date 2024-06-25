package roomescape.domain.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.ReservationTimeRepository;

@Repository
public class ReservationRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ReservationRowMapper rowMapper = new ReservationRowMapper();
  private final ReservationTimeRepository timeRepository;


  public ReservationRepository(JdbcTemplate jdbcTemplate, ReservationTimeRepository timeRepository) {
    this.jdbcTemplate = jdbcTemplate;
    this.timeRepository = timeRepository;
  }

  public List<Reservation> findAll() {
    return jdbcTemplate.query(
        "select * from reservation inner join reservation_time on reservation.time_id = reservation_time.id",
        rowMapper);
  }

  public Reservation getById(Long id) {
    return jdbcTemplate.queryForObject(
        "select * from reservation as r inner join reservation_time as rt on r.time_id = rt.id where r.id=?",
        rowMapper, id);
  }

  public Reservation save(CreateReservation newReservation) {
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    int updatedRow = jdbcTemplate.update(con -> {
      PreparedStatement statement = con.prepareStatement(
          "insert into reservation (name, date, time_id) values ( ?, ?, ? )", new String[]{"id"});
      statement.setString(1, newReservation.name());
      statement.setDate(2, Date.valueOf(newReservation.date()));
      statement.setLong(3, newReservation.timeId());
      return statement;
    }, generatedKeyHolder);
    return getById(generatedKeyHolder.getKeyAs(Long.class));
  }

  public void delete(long id) {
    jdbcTemplate.update("delete from reservation where id = ?", id);
  }

  class ReservationRowMapper implements RowMapper<Reservation> {


    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
      ReservationTime reservationTime = new ReservationTime(rs.getLong(5),
          rs.getTime(6).toLocalTime());
      return new Reservation(rs.getLong("reservation.id"), rs.getString("reservation.name"),
          rs.getDate("reservation.date").toLocalDate(), reservationTime);
    }
  }
}
