package roomescape.infra.reservation;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.theme.Theme;
import roomescape.domain.time.ReservationTime;

class ReservationEntity {
  private final long id;
  private String name;
  private LocalDate date;
  private ReservationTime time;
  private Theme theme;

  public ReservationEntity(long id) {
    this.id = id;
  }

  public ReservationEntity(long id, String name, LocalDate date, ReservationTime reservationTime, Theme theme) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = reservationTime;
    this.theme = theme;
  }

  public Reservation toDomain() {
    return new Reservation(id, name, date, time, theme);
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDate() {
    return date;
  }

  public ReservationTime getTime() {
    return time;
  }

  public Theme getTheme() {
    return theme;
  }
}
