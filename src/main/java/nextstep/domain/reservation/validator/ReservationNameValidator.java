package nextstep.domain.reservation.validator;

import nextstep.domain.reservation.Reservation.Name;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import org.springframework.stereotype.Component;

@Component
public class ReservationNameValidator implements
    ReservationCreateValidator {

  @Override
  public void validate(Create createReq) {
    /*
     * Constructor Validation
     * if (value == null || value.isEmpty())
     *    throw Exception();
     */
    Name.of(createReq.name());
  }
}
