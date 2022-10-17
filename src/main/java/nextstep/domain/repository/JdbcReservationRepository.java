package nextstep.domain.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.domain.ReservationEntity;
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
        .date(resultSet.getObject("date", LocalDate.class))
        .time(resultSet.getObject("time", LocalTime.class))
        .name(resultSet.getString("name"))
        .build();
    return entity;
  };

  @Override
  public ReservationEntity save(ReservationEntity reservation) {
    var keyHolder = new GeneratedKeyHolder();

    var sql = "INSERT INTO reservation (date, time, name) values (?, ?, ?)";
    jdbcTemplate.update(con -> {
      var ps = con.prepareStatement(sql, new String[]{"id"});
      ps.setObject(1, reservation.getDate());
      ps.setString(2, reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
      ps.setString(3, reservation.getName());
      return ps;
    }, keyHolder);

    return reservation.toBuilder()
        .id(keyHolder.getKey().longValue())
        .build();
  }

  @Override
  public List<ReservationEntity> findReservationsByDate(LocalDate date) {
    return null;
  }

  @Override
  public void deleteByDateAndTime(LocalDate date, LocalTime time) {

  }

  @Override
  public Optional<ReservationEntity> findReservationsByDateAndTime(LocalDate date, LocalTime time) {
    return Optional.empty();
  }
}
