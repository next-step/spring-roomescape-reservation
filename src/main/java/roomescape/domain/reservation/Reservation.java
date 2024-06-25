package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.domain.time.ReservationTime;

public class Reservation {
  private final long id;
  private String name;
  private LocalDate date;
  private ReservationTime reservationTime;

  public Reservation(long id) {
    this.id = id;
  }

  public Reservation(long id, String name, LocalDate date, ReservationTime reservationTime) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.reservationTime = reservationTime;
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

  public ReservationTime getReservationTime() {
    return reservationTime;
  }
}
