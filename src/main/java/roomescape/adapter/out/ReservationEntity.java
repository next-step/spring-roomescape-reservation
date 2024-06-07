package roomescape.adapter.out;

public class ReservationEntity {

  private final Long id;
  private final String name;
  private final String date;
  private final String time;

  public ReservationEntity(Long id, String name, String date, String time) {
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

  public static ReservationEntity of(Long id, String name, String date, String time) {
    return new ReservationEntity(id, name, date, time);
  }
}
