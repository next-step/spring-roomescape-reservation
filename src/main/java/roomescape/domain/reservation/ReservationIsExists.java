package roomescape.domain.reservation;

import java.time.LocalDate;
import roomescape.domain.time.ReservationTime;

public class ReservationIsExists extends RuntimeException {

  public ReservationIsExists(LocalDate date, ReservationTime time) {
    super(date.toString() + "  "+ time.getStartAt().toString()+" 에 예약이 이미 존재합니다.");
  }
}
