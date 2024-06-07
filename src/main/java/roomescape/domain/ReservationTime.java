package roomescape.domain;

public class ReservationTime {

  private final Long id;
  private final String startAt;

  public ReservationTime(Long id, String startAt) {
    this.id = id;
    this.startAt = startAt;
  }

  public Long getId() {
    return id;
  }

  public String getStartAt() {
    return startAt;
  }

  public static ReservationTime of(Long id, String startAt) {
    return new ReservationTime(id, startAt);
  }

  public static ReservationTime of(String startAt) {
    return new ReservationTime(null, startAt);
  }

  public ReservationTime addId(Long index) {
    return new ReservationTime(index, this.startAt);
  }
}
