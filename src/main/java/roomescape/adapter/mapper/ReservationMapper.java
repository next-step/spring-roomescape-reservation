package roomescape.adapter.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import roomescape.adapter.in.web.dto.ReservationCommand;
import roomescape.adapter.in.web.dto.ReservationResponse;
import roomescape.domain.Reservation;

public class ReservationMapper {

  private ReservationMapper() {
    throw new UnsupportedOperationException("생성 불가");
  }

  public static ReservationResponse mapToResponse(Reservation reservation) {
    LocalDateTime dateTime = reservation.getDateTime();
    String[] dateTimes = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                                 .split(" ");

    return ReservationResponse.of(reservation.getId(), reservation.getName(), dateTimes[0], dateTimes[1]);
  }

  public static Reservation mapToDomain(ReservationCommand reservationCommand) {

    return Reservation.of(reservationCommand.name(),
      createLocalDateTime(reservationCommand.date(), reservationCommand.time()));
  }

  private static LocalDateTime createLocalDateTime(String date, String time) {
    String dateTimeStr = String.format("%s %s", date, time);
    return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }
}
