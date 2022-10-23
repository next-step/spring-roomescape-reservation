package nextstep.application.reservation;

import java.util.Comparator;
import java.util.List;
import nextstep.application.reservation.dto.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationCreatePolicy {

  private final List<ReservationCreateValidation> reservationCreateValidations;

  public ReservationCreatePolicy(List<ReservationCreateValidation> reservationCreateValidations) {
    this.reservationCreateValidations = reservationCreateValidations.stream()
        .sorted(Comparator.comparingInt(ReservationCreateValidation::priority))
        .toList();
  }

  public void checkValid(Reservation reservation) {
    reservationCreateValidations.forEach(it -> it.checkValid(reservation));
  }
}
