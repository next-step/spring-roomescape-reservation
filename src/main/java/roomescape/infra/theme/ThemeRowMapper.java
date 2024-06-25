package roomescape.infra.theme;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.domain.theme.Theme;

@Component
class ThemeRowMapper implements RowMapper<Theme> {

  @Override
  public Theme mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new Theme(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
        rs.getString("thumbnail"));
  }
}