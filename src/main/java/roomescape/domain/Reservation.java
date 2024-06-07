package roomescape.domain;

import java.time.LocalDateTime;

public class Reservation {

  private final Long id;
  private final String name;
  private final LocalDateTime dateTime;

  public Reservation(Long id, String name, LocalDateTime dateTime) {
    this.id = id;
    this.name = name;
    this.dateTime = dateTime;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public static Reservation of(String name, LocalDateTime dateTime) {
    return new Reservation(null, name, dateTime);
  }

  public Reservation addId(Long index) {
    return new Reservation(index, this.name, this.dateTime);
  }
}
