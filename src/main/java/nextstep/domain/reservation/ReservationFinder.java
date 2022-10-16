package nextstep.domain.reservation;

import java.util.List;
import nextstep.domain.reservation.dto.ReservationFindCondition;

public interface ReservationFinder {

  default List<Reservation> findAll(ReservationFindCondition condition){
    throw new UnsupportedOperationException();
  }
}
