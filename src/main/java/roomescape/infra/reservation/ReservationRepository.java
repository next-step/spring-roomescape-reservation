package roomescape.infra.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.CreateReservation;
import roomescape.domain.reservation.Reservation;

@Repository
public class ReservationRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ReservationRowMapper rowMapper;

  public ReservationRepository(JdbcTemplate jdbcTemplate, ReservationRowMapper rowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.rowMapper = rowMapper;
  }

  public List<Reservation> findAll() {
    return jdbcTemplate.query(
        "select * from reservation inner join reservation_time on reservation.time_id = reservation_time.id inner join theme on theme.id= reservation.id",
        rowMapper).stream().map(ReservationEntity::toDomain).toList();
  }

  public boolean isExists(CreateReservation reservation) {
    return jdbcTemplate.queryForObject(
        "select count(id) from reservation where date=? and time_id=?",
        (rs, i) -> rs.getLong(1) > 0, reservation.date(), reservation.timeId());
  }

  public Reservation getById(Long id) {
    Reservation reservation = null;
    try {
      reservation = jdbcTemplate.queryForObject(
          "select * from reservation inner join reservation_time on reservation.time_id = reservation_time.id inner join theme on theme.id= reservation.id where reservation.id=?",
          rowMapper, id).toDomain();
    } catch (EmptyResultDataAccessException ignored) {

    }
    return reservation;
  }

  public long save(CreateReservation newReservation) {
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    int updatedRow = jdbcTemplate.update(con -> {
      PreparedStatement statement = con.prepareStatement(
          "insert into reservation (name, date, time_id, theme_id) values ( ?, ?, ?, ? )",
          new String[]{"id"});
      statement.setString(1, newReservation.name());
      statement.setDate(2, Date.valueOf(newReservation.date()));
      statement.setLong(3, newReservation.timeId());
      statement.setLong(4, newReservation.themeId());
      return statement;
    }, generatedKeyHolder);
    return generatedKeyHolder.getKeyAs(Long.class);
  }

  public void delete(long id) {
    jdbcTemplate.update("delete from reservation where id = ?", id);
  }
}
