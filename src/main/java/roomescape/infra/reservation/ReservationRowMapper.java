package roomescape.infra.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class ReservationRowMapper implements RowMapper<ReservationEntity> {

  @Override
  public ReservationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new ReservationEntity(rs.getLong("id"), rs.getString("name"),
        rs.getString("date"), rs.getLong("time_id"), rs.getLong("theme_id"));
  }
}