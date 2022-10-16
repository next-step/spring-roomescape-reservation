package nextstep.domain.reservation;

import java.util.List;
import nextstep.domain.reservation.dto.ReservationCommandDto;
import nextstep.domain.reservation.dto.ReservationFindCondition;

public interface ReservationRepository {

  default Reservation save(Reservation reservation) {
    throw new UnsupportedOperationException();
  }

  default boolean delete(ReservationCommandDto.Delete deleteReq) {
    throw new UnsupportedOperationException();
  }

  default List<Reservation> findAll(ReservationFindCondition condition) {
    throw new UnsupportedOperationException();
  }

}
