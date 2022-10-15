package nextstep.domain.reservation;

public interface ReservationRepository {

  default Reservation save(Reservation reservation){
    throw new UnsupportedOperationException();
  }
}
