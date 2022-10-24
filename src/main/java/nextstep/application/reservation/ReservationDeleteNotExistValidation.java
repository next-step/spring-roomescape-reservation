package nextstep.application.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.dto.ReservationDeleteValidationDto;
import nextstep.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationDeleteNotExistValidation implements ReservationDeleteValidation {

  private final ReservationRepository repository;

  @Override
  public void checkValid(ReservationDeleteValidationDto validationDto) {
    var reservation = repository.findReservationsByDateAndTime(validationDto.date(), validationDto.time());
    if (reservation.isEmpty()) {
      throw new IllegalArgumentException(String.format("예약이 존재하지 않습니다. 날짜: %s, 시간: %s",
          validationDto.date(), validationDto.time()));
    }
  }

  @Override
  public int priority() {
    return 1;
  }
}
