package roomescape.domain.theme;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeRepository {
  private final JdbcTemplate template;

  public ThemeRepository(JdbcTemplate template) {
    this.template = template;
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
}
