package roomescape.domain.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.theme.Theme;
import roomescape.domain.time.ReservationTime;

class ReservationRowMapper implements RowMapper<Reservation> {
  @Override
  public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
    ReservationTime reservationTime = new ReservationTime(rs.getLong(5),
        rs.getTime(6).toLocalTime());
    Theme theme = new Theme(rs.getLong(7), rs.getString(8), rs.getString(9), rs.getString(10));
    return new Reservation(rs.getLong("reservation.id"), rs.getString("reservation.name"),
        rs.getDate("reservation.date").toLocalDate(), reservationTime, theme);
  }
}