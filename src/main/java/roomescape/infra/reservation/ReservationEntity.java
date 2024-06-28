package roomescape.infra.reservation;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.theme.ThemeSummary;
import roomescape.domain.time.ReservationTime;

class ReservationEntity {
  private final long id;
  private String name;
  private String date;
  private long timeId;
  private long themeId;

  public ReservationEntity(long id, String name, String date, long timeId, long themeId) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.timeId = timeId;
    this.themeId = themeId;
  }

  Reservation toDomain(ThemeSummary theme, ReservationTime time) {
    return new Reservation(id, name, LocalDate.parse(date), time, theme);
  }
  public long getId() {
    return id;
  }

  public long getTimeId() {
    return timeId;
  }

  public long getThemeId() {
    return themeId;
  }
}
