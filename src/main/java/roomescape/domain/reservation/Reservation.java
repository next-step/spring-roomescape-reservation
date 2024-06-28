package roomescape.domain.reservation;

import java.time.LocalDate;
import roomescape.domain.theme.ThemeSummary;
import roomescape.domain.time.ReservationTime;

public class Reservation {

  private final long id;
  private String name;
  private LocalDate date;
  private ReservationTime time;
  private ThemeSummary theme;

  public Reservation(long id) {
    this.id = id;
  }

  public Reservation(long id, String name, LocalDate date, ReservationTime reservationTime,
      ThemeSummary theme) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = reservationTime;
    this.theme = theme;
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

  public ThemeSummary getTheme() {
    return theme;
  }
}
