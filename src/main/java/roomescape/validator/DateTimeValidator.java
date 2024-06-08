package roomescape.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeValidator {

  private DateTimeValidator() {
    throw new UnsupportedOperationException("생성 불가");
  }

  public static void previousDateTimeCheck(String date, String time) {
    LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);

    if (dateTime.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("현재 시간 이전의 예약은 불가능합니다.");
    }

  }
}
