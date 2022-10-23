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
import nextstep.domain.schedule.Schedule;
import nextstep.domain.schedule.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservationRepositoryImpl implements ReservationRepository {

  ScheduleRepository scheduleRepository;

  static RowMapper<Schedule> scheduleRowMapper = (resultSet, rowNum) -> Schedule.builder()
      .id(resultSet.getLong("schedule_id"))
      .date(resultSet.getDate("date").toLocalDate())
      .time(resultSet.getTime("time").toLocalTime())
      .build();

  static RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> Reservation.builder()
      .id(resultSet.getLong("id"))
      .schedule(scheduleRowMapper.mapRow(resultSet, rowNum))
      .name(Name.of(resultSet.getString("name")))
      .build();

  JdbcTemplate jdbcTemplate;

  @Override
  public Reservation save(Create create) {
    Long scheduleId = create.scheduleId();
    String name = create.name();

    Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
    String saveQuery = """
        insert into reservation (schedule_id, name) values (?, ?)
        """;

    String getIdQuery = "select last_insert_id ()";
    Long entityId = jdbcTemplate.queryForObject(getIdQuery, Long.class);

    jdbcTemplate.update(saveQuery, scheduleId, name);
    return Reservation.builder()
        .id(entityId)
        .schedule(schedule)
        .name(Name.of(name))
        .build();
  }

  // TODO : 정렬 로직은 어디서?
  @Override
  public List<Reservation> findAll(ReservationFindCondition condition) {
    String sql = """
        select r.id, r.schedule_id, r.name, s.id schedule_id, s.date, s.time, s.theme_id
        from reservation r
          inner join schedule s
            on r.schedule_id = s.id
        where r.schedule_id = ?
        order by date
        """;

    return jdbcTemplate.query(sql, reservationRowMapper, condition.scheduleId());
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
        where 
          schedule_id in (
            select id 
            from schedule 
            where date = ? and time = ?
          )
        """;
    int update = jdbcTemplate.update(sql, date, time);
    return update > 0;
  }
}
