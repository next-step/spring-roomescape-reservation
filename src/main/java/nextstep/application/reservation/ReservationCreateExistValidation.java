package nextstep.application.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.dto.Reservation;
import nextstep.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreateExistValidation implements ReservationCreateValidation {

  private final ReservationRepository repository;

  @Override
  public void checkValid(Reservation reservation) {
    var entity = repository.findReservationsByDateAndTime(reservation.date(), reservation.time());
    if (entity.isPresent()) {
      throw new IllegalArgumentException("이미 해당 날짜, 시간에 예약이 존재합니다.");
    }
  }

  @Override
  public int priority() {
    return 1;
  }
}
