package roomescape.adapter.in.web.dto;

public record ReservationResponse(Long id, String name, String date, String time) {

  public static ReservationResponse of(Long id, String name, String date, String time) {
    return new ReservationResponse(id, name, date, time);
  }
}
