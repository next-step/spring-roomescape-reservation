package roomescape.infra.theme;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.domain.theme.ThemeSummary;

@Component
class ThemeSummaryRowMapper implements RowMapper<ThemeSummary> {

  @Override
  public ThemeSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new ThemeSummary(rs.getLong("id"), rs.getString("name"));
  }
}
