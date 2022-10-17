package nextstep.application.reservation;

import nextstep.application.reservation.dto.Reservation;

public interface ReservationValidation {

  void checkValid(Reservation reservation);

  int priority();
}
