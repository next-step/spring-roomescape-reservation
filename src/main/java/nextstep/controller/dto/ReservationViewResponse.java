package nextstep.controller.dto;

import nextstep.domain.reservation.Reservation.Name;

public class ReservationViewResponse {

  public record Reservation(
      ScheduleViewResponse.SimpleSchedule schedule,
      Name name
  ) {

    public static Reservation of(nextstep.domain.reservation.Reservation domain) {
      return new Reservation(ScheduleViewResponse.SimpleSchedule.of(domain.getSchedule()), domain.getName());
    }
  }
}