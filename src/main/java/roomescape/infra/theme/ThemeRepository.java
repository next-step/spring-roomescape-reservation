package roomescape.infra.theme;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.theme.CreateTheme;
import roomescape.domain.theme.Theme;

@Repository
public class ThemeRepository {

  private final JdbcTemplate template;
  private final ThemeRowMapper rowMapper;

  public ThemeRepository(JdbcTemplate template, ThemeRowMapper rowMapper) {
    this.template = template;
    this.rowMapper = rowMapper;
  }

  public boolean isUsedInReservation(Theme theme) {
    return template.queryForObject("select exists(id) from reservation where theme_id = ?",
        (rs, i) -> rs.getBoolean(1), theme.getId());
  }

  public Theme create(CreateTheme theme) {
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    template.update(con -> {
      PreparedStatement statement = con.prepareStatement(
          "insert into theme (name, description, thumbnail) values ( ?,?,? )", new String[]{"id"});
      statement.setString(1, theme.name());
      statement.setString(2, theme.description());
      statement.setString(3, theme.thumbnail());
      return statement;
    }, generatedKeyHolder);
    return theme.toDomain(generatedKeyHolder.getKey().longValue());
  }

  public List<Theme> findAll() {
    return template.query("select * from theme", rowMapper);
  }

  public Theme findOne(long id) {
    Theme theme = null;
    try {
      theme = template.queryForObject("select * from theme where id=?", rowMapper, id);
    } catch (EmptyResultDataAccessException ignored) {

    }
    return theme;
  }

  public void delete(long id) {
    template.update("delete from theme where id=?", id);
  }


}
