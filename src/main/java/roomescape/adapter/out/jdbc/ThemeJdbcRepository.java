package roomescape.adapter.out.jdbc;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.adapter.mapper.ThemeMapper;
import roomescape.adapter.out.ThemeEntity;
import roomescape.application.port.out.ThemePort;
import roomescape.domain.Theme;

@Repository
public class ThemeJdbcRepository implements ThemePort {

  private final JdbcTemplate jdbcTemplate;

  public ThemeJdbcRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Theme saveTheme(Theme theme) {
    String sql = "INSERT INTO theme(name, description, thumbnail) VALUES(?, ?, ?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      ps.setString(1, theme.getName());
      ps.setString(2, theme.getDescription());
      ps.setString(3, theme.getThumbnail());

      return ps;
    }, keyHolder);

    return theme.addId(Objects.requireNonNull(keyHolder.getKey())
                              .longValue());
  }

  @Override
  public List<Theme> findThemes() {
    String sql = "select id, name, description, thumbnail from theme";

    List<ThemeEntity> themeEntities = jdbcTemplate.query(
      sql, (resultSet, rowNum) -> {
        ThemeEntity themeEntity = new ThemeEntity(
          resultSet.getLong("id"),
          resultSet.getString("name"),
          resultSet.getString("description"),
          resultSet.getString("thumbnail")
        );

        return themeEntity;
      }
    );

    return themeEntities.stream()
                        .map(ThemeMapper::mapToDomain)
                        .toList();
  }

  @Override
  public void deleteTheme(Long id) {
    String sql = "DELETE FROM theme WHERE id = ?";

    jdbcTemplate.update(sql, id);
  }

  @Override
  public Integer countThemeById(Long id) {
    String sql = "SELECT COUNT(*) FROM theme WHERE id = ?";

    return jdbcTemplate.queryForObject(sql, Integer.class);
  }
}
