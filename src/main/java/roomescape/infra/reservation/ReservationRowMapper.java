package roomescape.infra.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.theme.Theme;
import roomescape.domain.time.ReservationTime;

@Component
class ReservationRowMapper implements RowMapper<Reservation> {
  @Override
  public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
    ReservationTime reservationTime = new ReservationTime(rs.getLong("reservation_time.id"),
        rs.getTime("reservation_time.start_at").toLocalTime());
    Theme theme = new Theme(rs.getLong("theme.id"), rs.getString("theme.name"), rs.getString("theme.description"), rs.getString("theme.thumbnail"));
    return new Reservation(rs.getLong("reservation.id"), rs.getString("reservation.name"),
        rs.getDate("reservation.date").toLocalDate(), reservationTime, theme);
  }
}