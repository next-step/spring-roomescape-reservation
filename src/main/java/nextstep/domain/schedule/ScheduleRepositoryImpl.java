package nextstep.domain.schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.domain.schedule.dto.ScheduleCommandDto;
import nextstep.domain.schedule.dto.ScheduleFindCondition;
import nextstep.domain.schedule.validator.ScheduleCreateValidator;
import nextstep.domain.theme.Theme;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleRepositoryImpl implements ScheduleRepository {

  static RowMapper<Theme> themeRowMapper = (resultSet, rowNum) -> Theme.builder()
      .id(resultSet.getLong("theme_id"))
      .name(resultSet.getString("name"))
      .description(resultSet.getString("description"))
      .price(BigDecimal.valueOf(resultSet.getLong("price")))
      .build();

  static RowMapper<Schedule> scheduleRowMapper = (resultSet, rowNum) -> Schedule.builder()
      .id(resultSet.getLong("id"))
      .theme(themeRowMapper.mapRow(resultSet, rowNum))
      .date(resultSet.getDate("date").toLocalDate())
      .time(resultSet.getTime("time").toLocalTime())
      .build();

  JdbcTemplate jdbcTemplate;
  Set<ScheduleCreateValidator> scheduleCreateValidators;

  @Override
  public Schedule save(ScheduleCommandDto.Create createReq) {

    scheduleCreateValidators.forEach(
        scheduleCreateValidator -> scheduleCreateValidator.validate(createReq)
    );

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
        .theme(Theme.builder()
            .id(themeId)
            // FIXME : Theme 바인딩
            .price(BigDecimal.ZERO)
            .name("TEMP")
            .description("")
            .build())
        .date(date)
        .time(time)
        .build();
  }

  // TODO : 정렬 로직은 어디서?
  @Override
  public List<Schedule> findAll(ScheduleFindCondition findCondition) {
    StringBuilder sqlBuilder = new StringBuilder("""
        select s.id, s.theme_id, date, time, t.name, t.price, t.description
        from schedule s
          inner join theme t
          on s.theme_id = t.id
        where 1=1
        """);

    if (findCondition.existThemeId()) {
      // add themeId condition
      sqlBuilder.append("""
            and theme_id = %d
          """.formatted(findCondition.themeId()));
    }

    if (findCondition.existDate()) {
      // add Date condition
      sqlBuilder.append("""
            and date = %s
          """.formatted(findCondition.date()));
    }

    String sql = sqlBuilder.append("""
        order by id
        """).toString();

    return jdbcTemplate.query(sql, scheduleRowMapper);
  }

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

  @Override
  public Optional<Schedule> findById(Long id) {
    String sql = """
        select s.id, s.theme_id, date, time, t.name, t.price, t.description
             from schedule s
               inner join theme t
               on s.theme_id = t.id
             where s.id = ? 
             """;

    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(sql, scheduleRowMapper, id));
    } catch (IncorrectResultSizeDataAccessException e) {
      return Optional.empty();
    }
  }
}
