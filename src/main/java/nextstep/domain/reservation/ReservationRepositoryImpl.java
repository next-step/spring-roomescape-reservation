package nextstep.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.domain.reservation.Reservation.Name;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import nextstep.domain.reservation.dto.ReservationCommandDto.Delete;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservationRepositoryImpl implements ReservationRepository {

  static RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> Reservation.builder()
      .id(resultSet.getLong("id"))
      .date(resultSet.getDate("date").toLocalDate())
      .time(resultSet.getTime("time").toLocalTime())
      .name(Name.of(resultSet.getString("name")))
      .build();

  JdbcTemplate jdbcTemplate;

  @Override
  public Reservation save(Create create) {
    LocalDate date = create.date();
    LocalTime time = create.time();
    String name = create.name();

    String saveQuery = """
        insert into reservation (date, time, name) values (?, ?, ?)
        """;

    jdbcTemplate.update(saveQuery, date, time, name);

    String getIdQuery = "select last_insert_id ()";
    Long entityId = jdbcTemplate.queryForObject(getIdQuery, Long.class);

    return Reservation.builder()
        .id(entityId)
        .name(Name.of(name))
        .date(date)
        .time(time)
        .build();
  }

  // TODO : 정렬 로직은 어디서?
  @Override
  public List<Reservation> findAll(ReservationFindCondition condition) {
    String sql = """
        select id, date, time, name 
        from reservation 
        where date = ?
        order by date
        """;

    return jdbcTemplate.query(sql, reservationRowMapper, condition.date());
  }

  /**
   * @param deleteReq
   * @return 실제로 삭제 되었는지 여부. 삭제 되었으면 true, 그렇지 않으면 false
   */
  @Override
  public boolean delete(Delete deleteReq) {
    LocalDate date = deleteReq.date();
    LocalTime time = deleteReq.time();

    String sql = """
        delete 
        from reservation 
        where date = ? and time = ?
        """;
    int update = jdbcTemplate.update(sql, date, time);
    return update > 0;
  }
}
