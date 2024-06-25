package roomescape.domain.theme;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class ThemeRowMapper implements RowMapper<Theme> {

  @Override
  public Theme mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new Theme(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
        rs.getString("thumbnail"));
  }
}