package nextstep.application.reservation;

import nextstep.application.reservation.dto.Reservation;

public interface ReservationCreateValidation {

  void checkValid(Reservation reservation);

  int priority();
}
