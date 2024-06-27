package roomescape.domain.time;

public class ReservationTimeIsUsed extends RuntimeException {

  public ReservationTimeIsUsed(ReservationTime time) {
    super(time.getId() + "번 "+ time.getStartAt() + "시간대를 사용중인 예약이 존재합니다.");
  }
}
