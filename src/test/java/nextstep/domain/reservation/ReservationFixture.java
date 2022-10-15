package nextstep.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;


public class ReservationFixture {

  /**
   * Fixture 이름 -> 성시형
   */
  public final static String NAME = "성시형";
  /**
   * Fixture 날짜 -> now().plusDays(1);
   */
  public final static LocalDate DATE = LocalDate.now().plusDays(1);
  /**
   * Fixture 시간 -> now();
   */
  public final static LocalTime TIME = LocalTime.now();

  /**
   * 다음날 이 시간의 예약.
   * <pre>
   *   Reservation(date= now + 1day, time = now, name = 성시형)
   * </pre>
   */
  public final static Reservation FIXTURE1 = Reservation.builder()
      .name(NAME)
      .date(DATE)
      .time(TIME)
      .build();
}
