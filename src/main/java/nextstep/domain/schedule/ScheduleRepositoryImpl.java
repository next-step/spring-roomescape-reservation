package nextstep.domain.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.domain.schedule.dto.ScheduleCommandDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleRepositoryImpl implements ScheduleRepository {

  static RowMapper<Schedule> scheduleRowMapper = (resultSet, rowNum) -> Schedule.builder()
      .id(resultSet.getLong("id"))
      .themeId(resultSet.getLong("theme_id"))
      .date(resultSet.getDate("date").toLocalDate())
      .time(resultSet.getTime("time").toLocalTime())
      .build();

  JdbcTemplate jdbcTemplate;

  @Override
  public Schedule save(ScheduleCommandDto.Create createReq) {

    Long themeId = createReq.themeId();
    LocalDate date = createReq.date();
    LocalTime time = createReq.time();

    String saveQuery = """
        insert into schedule (theme_id, date, time) values (?, ?, ?)
        """;

    jdbcTemplate.update(saveQuery, themeId, date, time);

    String getIdQuery = "select last_insert_id ()";
    Long entityId = jdbcTemplate.queryForObject(getIdQuery, Long.class);

    return Schedule.builder()
        .id(entityId)
        .themeId(themeId)
        .date(date)
        .time(time)
        .build();
  }

  // TODO : 정렬 로직은 어디서?
  @Override
  public List<Schedule> findAll() {
    String sql = """
        select id, theme_id, date, time
        from schedule 
        order by id
        """;

    return jdbcTemplate.query(sql, scheduleRowMapper);
  }

  /**
   * @param deleteReq
   * @return 실제로 삭제 되었는지 여부. 삭제 되었으면 true, 그렇지 않으면 false
   */
  @Override
  public boolean delete(ScheduleCommandDto.Delete deleteReq) {
    String sql = """
        delete 
        from schedule 
        where id = ?
        """;

    int update = jdbcTemplate.update(sql, deleteReq.id());
    return update > 0;
  }

}
