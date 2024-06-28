package roomescape.infra.theme;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class ThemeRowMapper implements RowMapper<ThemeEntity> {

  @Override
  public ThemeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new ThemeEntity(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
        rs.getString("thumbnail"));
  }
}