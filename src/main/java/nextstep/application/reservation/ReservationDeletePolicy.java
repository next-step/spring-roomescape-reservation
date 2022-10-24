package nextstep.application.reservation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.dto.ReservationDeleteValidationDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationDeletePolicy {

  private final List<ReservationDeleteValidation> validations;

  public void checkValid(ReservationDeleteValidationDto validationDto) {
    validations.forEach(it -> it.checkValid(validationDto));
  }
}
