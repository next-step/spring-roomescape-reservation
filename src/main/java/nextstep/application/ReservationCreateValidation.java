package nextstep.application;

import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import nextstep.application.dto.Reservation;
import nextstep.domain.repository.ReservationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreateValidation implements ReservationValidation {

  private final ReservationRepository repository;

  @Override
  public void checkValid(Reservation reservation) {
    var entity = repository.findReservationsByDateAndTime(reservation.date(),
        LocalTime.parse(reservation.time()));
    if (entity.isPresent()) {
      throw new IllegalArgumentException("이미 해당 날짜, 시간에 예약이 존재합니다.");
    }
  }

  @Override
  public int priority() {
    return 1;
  }
}
