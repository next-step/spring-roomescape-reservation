package nextstep.schedules.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import nextstep.schedules.Schedule;
import nextstep.themes.Themes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDao {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Long themeId, LocalDate date, LocalTime time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO schedules(themes_id, date, time) VALUES(?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setLong(1, themeId);
            preparedStatement.setObject(2, date);
            preparedStatement.setObject(3, time);
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Schedule> findWithThemesByThemeIdAndDate(Long themeId, LocalDate date) {
        String sql = "SELECT * FROM schedules where themes_id=? AND date=?";
        List<ScheduleDto> scheduleDtos = jdbcTemplate.query(sql,
                                                     (rs, rowNum) -> new ScheduleDto(
                                                             rs.getLong("id"),
                                                             rs.getLong("themes_id"),
                                                             rs.getObject("date", LocalDate.class),
                                                             rs.getObject("time", LocalTime.class)
                                                     ), themeId, date);

        String themeIds = scheduleDtos.stream()
                                      .map(scheduleDto -> scheduleDto.themeId.toString())
                                      .distinct()
                                      .collect(Collectors.joining(","));

        Map<Long, Themes> themesByIds = findThemesInIds(themeIds);
        return scheduleDtos.stream()
                .map(scheduleDto -> new Schedule(scheduleDto.id, themesByIds.get(scheduleDto.themeId), scheduleDto.date, scheduleDto.time))
                .collect(Collectors.toList());
    }

    private Map<Long, Themes> findThemesInIds(String themeIds) {
        String sql = "SELECT * FROM themes WHERE id IN (?)";
        return jdbcTemplate.query(sql,
                                  (rs, rowNum) -> new Themes(
                                          rs.getLong("id"),
                                          rs.getString("name"),
                                          rs.getString("desc"),
                                          rs.getLong("price")
                                  ), themeIds)
                .stream().collect(Collectors.toMap(Themes::getId, Function.identity()));
    }

    private static class ScheduleDto {

        private final Long id;
        private final Long themeId;
        private final LocalDate date;
        private final LocalTime time;

        private ScheduleDto(Long id, Long themeId, LocalDate date, LocalTime time) {
            this.id = id;
            this.themeId = themeId;
            this.date = date;
            this.time = time;
        }
    }
}
