package nextstep.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.dto.Reservation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationPolicy {

  private final List<ReservationValidation> reservationValidations;

  public void checkValid(Reservation reservation) {
    reservationValidations.forEach(it -> it.checkValid(reservation));
  }
}
