package nextstep.domain.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.domain.reservation.ReservationEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcReservationRepository implements ReservationRepository {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<ReservationEntity> rowMapper = (resultSet, rowNum) -> {
    var entity = ReservationEntity.builder()
        .id(resultSet.getLong("id"))
        .scheduleId(resultSet.getLong("schedule_id"))
        .themeId(resultSet.getLong("theme_id"))
        .date(resultSet.getObject("date", LocalDate.class))
        .time(resultSet.getObject("time", LocalTime.class))
        .name(resultSet.getString("name"))
        .build();
    return entity;
  };

  @Override
  public ReservationEntity save(ReservationEntity reservation) {
    var keyHolder = new GeneratedKeyHolder();

    var sql = "INSERT INTO reservation (schedule_id, theme_id, date, time, name) values (?, ?, ?, ?, ?)";
    jdbcTemplate.update(con -> {
      var ps = con.prepareStatement(sql, new String[]{"id"});
      ps.setObject(1, reservation.getScheduleId());
      ps.setObject(2, reservation.getThemeId());
      ps.setObject(3, reservation.getDate());
      ps.setObject(4, reservation.getTime());
      ps.setString(5, reservation.getName());
      return ps;
    }, keyHolder);

    return reservation.toBuilder()
        .id(keyHolder.getKey().longValue())
        .build();
  }

  @Override
  public List<ReservationEntity> findReservationsByDate(LocalDate date) {
    var sql = "SELECT * FROM reservation where date = ?";
    return jdbcTemplate.query(sql, rowMapper, date);
  }

  @Override
  public void deleteByDateAndTime(LocalDate date, LocalTime time) {
    var sql = "DELETE FROM reservation where date = ? and time = ?";
    jdbcTemplate.update(sql, date, time);
  }

  @Override
  public Optional<ReservationEntity> findReservationsByDateAndTime(LocalDate date, LocalTime time) {
    var sql = "SELECT * FROM reservation where date = ? and time = ?";
    try {
      var reservationEntity = jdbcTemplate.queryForObject(sql, rowMapper, date, time);
      return Optional.ofNullable(reservationEntity);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ReservationEntity> getReservation(Long id) {
    var sql = "SELECT * FROM reservation where id = ?";
    try {
      var reservationEntity = jdbcTemplate.queryForObject(sql, rowMapper, id);
      return Optional.ofNullable(reservationEntity);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ReservationEntity> findReservationByThemeId(Long themeId) {
    var sql = "SELECT * FROM reservation where theme_id = ?";
    try {
      var reservationEntity = jdbcTemplate.queryForObject(sql, rowMapper, themeId);
      return Optional.ofNullable(reservationEntity);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ReservationEntity> findReservationByScheduleId(Long scheduleId) {
    var sql = "SELECT * FROM reservation where schedule_id = ?";
    try {
      var reservationEntity = jdbcTemplate.queryForObject(sql, rowMapper, scheduleId);
      return Optional.ofNullable(reservationEntity);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
