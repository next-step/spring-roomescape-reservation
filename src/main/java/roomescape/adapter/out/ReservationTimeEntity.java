package roomescape.adapter.out;

public class ReservationTimeEntity {

  private final Long id;
  private final String startAt;

  public ReservationTimeEntity(Long id, String startAt) {
    this.id = id;
    this.startAt = startAt;
  }

  public static ReservationTimeEntity of(Long id, String startAt) {
    return new ReservationTimeEntity(id, startAt);
  }

  public Long getId() {
    return id;
  }

  public String getStartAt() {
    return startAt;
  }
}
