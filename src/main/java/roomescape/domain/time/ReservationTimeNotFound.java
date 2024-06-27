package roomescape.domain.time;

public class ReservationTimeNotFound extends RuntimeException {

  public ReservationTimeNotFound(long id) {
    super(id+"번 예약 시간이 존재하지 않습니다.");
  }
}
