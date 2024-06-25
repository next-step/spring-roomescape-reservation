package roomescape.domain.time;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class ReservationTimeRowMapper implements RowMapper<ReservationTime> {

  @Override
  public ReservationTime mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new ReservationTime(rs.getLong("id"), rs.getTime("start_at").toLocalTime());
  }
}