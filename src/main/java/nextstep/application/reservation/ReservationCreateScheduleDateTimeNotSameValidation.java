package nextstep.application.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.dto.Reservation;
import nextstep.application.schedule.ScheduleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationCreateScheduleDateTimeNotSameValidation implements ReservationCreateValidation {

  private final ScheduleService scheduleService;

  @Override
  public void checkValid(Reservation reservation) {
    // 2번 validation에 존재하지 않음을 증명하기에, get으로 가져옴.
    var schedule = scheduleService.getSchedule(reservation.scheduleId()).get();
    var reservationDate = reservation.date();
    var reservationTime = reservation.time();
    var scheduleDate = schedule.date();
    var scheduleTime = schedule.time();
    if (!isSameDate(reservationDate, scheduleDate) || !isSameTime(reservationTime, scheduleTime)) {
      throw new IllegalArgumentException(String.format("예약하러는 스케쥴의 시각이 다릅니다. 예약 날짜: %s 시간: %s, 스케쥴 날짜: %s 시간: %s",
          reservationDate, reservationTime, scheduleDate, scheduleTime));
    }
  }

  @Override
  public int priority() {
    return 3;
  }

  private boolean isSameDate(LocalDate source, LocalDate target) {
    return Objects.equals(source, target);
  }


  private boolean isSameTime(LocalTime source, LocalTime target) {
    return Objects.equals(source, target);
  }
}
