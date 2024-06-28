package roomescape.infra.time;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class ReservationTimeRowMapper implements RowMapper<ReservationTimeEntity> {

  @Override
  public ReservationTimeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new ReservationTimeEntity(rs.getLong("id"), rs.getString("start_at"));
  }
}