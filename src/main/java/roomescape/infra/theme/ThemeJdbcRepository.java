package roomescape.infra.theme;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.theme.CreateTheme;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeSummary;

@Repository
public class ThemeJdbcRepository {

  private final JdbcTemplate template;
  private final NamedParameterJdbcTemplate namedTemplate;
  private final ThemeRowMapper rowMapper;
  private final ThemeSummaryRowMapper summaryRowMapper;

  public ThemeJdbcRepository(JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate,
      ThemeRowMapper rowMapper, ThemeSummaryRowMapper summaryRowMapper) {
    this.template = template;
    this.namedTemplate = namedTemplate;
    this.rowMapper = rowMapper;
    this.summaryRowMapper = summaryRowMapper;
  }

  public boolean isUsedInReservation(Theme theme) {
    return Boolean.TRUE.equals(
        template.queryForObject("select count(id) from reservation where theme_id = ? limit 1",
            (rs, i) -> rs.getLong(1) > 0, theme.getId()));
  }

  public long create(CreateTheme theme) {
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    template.update(con -> {
      PreparedStatement statement = con.prepareStatement(
          "insert into theme (name, description, thumbnail) values ( ?,?,? )", new String[]{"id"});
      statement.setString(1, theme.name());
      statement.setString(2, theme.description());
      statement.setString(3, theme.thumbnail());
      return statement;
    }, generatedKeyHolder);
    return generatedKeyHolder.getKey().longValue();
  }

  public List<ThemeEntity> findList() {
    return template.query("select id, name, description, thumbnail from theme", rowMapper);
  }

  public List<ThemeSummary> findSummaryByIds(Collection<Long> ids) {
    SqlParameterSource parameterSource = new MapSqlParameterSource("ids", ids);
    return namedTemplate.query("select id, name from theme where id in (:ids)", parameterSource,
        summaryRowMapper);
  }

  public ThemeSummary findSummary(long id) {
    return template.queryForObject("select id, name from theme where id=?", summaryRowMapper, id);
  }

  public List<ThemeSummary> findSummaries() {
    return template.query("select id, name from theme", summaryRowMapper);
  }

  public Theme findOne(long id) {
    Theme theme = null;
    try {
      theme = template.queryForObject(
              "select id, name, description, thumbnail from theme where id=?", rowMapper, id)
          .toDomain();
    } catch (EmptyResultDataAccessException ignored) {

    }
    return theme;
  }

  public void delete(long id) {
    template.update("delete from theme where id=?", id);
  }
}
