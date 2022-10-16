package nextstep.application;

import nextstep.application.dto.Reservation;

public interface ReservationValidation {

  void checkValid(Reservation reservation);

  int priority();
}
