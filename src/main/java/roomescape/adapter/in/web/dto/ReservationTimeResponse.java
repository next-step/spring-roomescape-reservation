package roomescape.adapter.in.web.dto;

public record ReservationTimeResponse(Long id, String startAt) {

  public static ReservationTimeResponse of(Long id, String startAt) {
    return new ReservationTimeResponse(id, startAt);
  }
}
