package roomescape.adapter.in.web.dto;

public record ReservationCommand(String name, String date, String time) {

  @Override
  public String name() {
    return name;
  }

  @Override
  public String date() {
    return date;
  }

  @Override
  public String time() {
    return time;
  }
}
