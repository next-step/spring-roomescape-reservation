package nextstep.domain.schedule.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.domain.schedule.ScheduleEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcScheduleRepository implements ScheduleRepository {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<ScheduleEntity> rowMapper = (resultSet, rowNum) -> {
    var entity = ScheduleEntity.builder()
        .id(resultSet.getLong("id"))
        .themeId(resultSet.getLong("theme_id"))
        .date(resultSet.getObject("date", LocalDate.class))
        .time(resultSet.getObject("time", LocalTime.class))
        .build();
    return entity;
  };

  @Override
  public ScheduleEntity save(ScheduleEntity schedule) {
    var keyHolder = new GeneratedKeyHolder();
    var sql = "insert into schedule (theme_id, date, time) values (?, ?, ?)";
    jdbcTemplate.update(con -> {
      var ps = con.prepareStatement(sql, new String[]{"id"});
      ps.setLong(1, schedule.getThemeId());
      ps.setString(2, schedule.getDate().toString());
      ps.setString(3, schedule.getTime().toString());
      return ps;
    }, keyHolder);
    return schedule.toBuilder()
        .id(keyHolder.getKey().longValue())
        .build();
  }

  @Override
  public Optional<ScheduleEntity> findSchedule(Long themeId, LocalDate date, LocalTime time) {
    var sql = "select * from schedule where theme_id = ? and date = ? and time = ?";
    try {
      var entity = jdbcTemplate.queryForObject(sql, rowMapper, themeId, date, time);
      return Optional.ofNullable(entity);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<ScheduleEntity> findSchedules(Long themeId, LocalDate date) {
    var sql = "select * from schedule where theme_id = ? and date = ?";
    return jdbcTemplate.query(sql, rowMapper, themeId, date);
  }
}
