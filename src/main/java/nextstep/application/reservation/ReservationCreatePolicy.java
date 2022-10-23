package nextstep.application.reservation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.dto.Reservation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationCreatePolicy {

  private final List<ReservationCreateValidation> reservationCreateValidations;

  public void checkValid(Reservation reservation) {
    reservationCreateValidations.forEach(it -> it.checkValid(reservation));
  }
}
