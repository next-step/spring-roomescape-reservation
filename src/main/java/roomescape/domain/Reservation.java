package roomescape.domain;

public class Reservation {

  private final Long id;
  private final String name;
  private final String date;
  private final String time;

  public Reservation(Long id, String name, String date, String time) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDate() {
    return date;
  }

  public String getTime() {
    return time;
  }

  public Reservation addId(Long index) {
    return new Reservation(index, this.name, this.date, this.time);
  }

  public static Reservation of(Long id, String name, String date, String time) {
    return new Reservation(id, name, date, time);
  }

  public static Reservation of(String name, String date, String time) {
    return new Reservation(null, name, date, time);
  }
}
