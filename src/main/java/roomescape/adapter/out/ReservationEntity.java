package roomescape.adapter.out;

public class ReservationEntity {

  private final Long id;
  private final String name;
  private final String date;
  private final ReservationTimeEntity time;
  private final ThemeEntity theme;

  public ReservationEntity(Long id, String name, String date, ReservationTimeEntity time, ThemeEntity theme) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
    this.theme = theme;
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

  public ReservationTimeEntity getTime() {
    return time;
  }

  public ThemeEntity getTheme() {
    return theme;
  }

  public static ReservationEntity of(Long id, String name, String date, ReservationTimeEntity time) {
    return new ReservationEntity(id, name, date, time, null);
  }
}
