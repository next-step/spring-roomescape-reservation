package nextstep.domain.reservation.validator;

import nextstep.domain.reservation.dto.ReservationCommandDto;

public interface ReservationCreateValidator {

  default void validate(ReservationCommandDto.Create createReq) {
    throw new UnsupportedOperationException();
  }
}
