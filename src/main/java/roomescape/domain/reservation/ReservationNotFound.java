package roomescape.domain.reservation;

public class ReservationNotFound extends RuntimeException {

  public ReservationNotFound(long id) {
    super(id+"번 예약은 존재하지 않는 예약입니다.");
  }
}
