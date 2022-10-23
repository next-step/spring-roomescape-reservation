package nextstep.application.reservation;

import nextstep.application.reservation.dto.ReservationDeleteValidationDto;

public interface ReservationDeleteValidation {

  void checkValid(ReservationDeleteValidationDto validationDto);

  int priority();
}
