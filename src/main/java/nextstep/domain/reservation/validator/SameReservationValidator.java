package nextstep.domain.reservation.validator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nextstep.domain.reservation.ReservationFinder;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SameReservationValidator implements ReservationCreateValidator {

  ReservationFinder reservationFinder;

  @Override
  public void validate(Create createReq) {
    var reservations = reservationFinder.findAll(ReservationFindCondition.builder()
        .date(createReq.date())
        .time(createReq.time())
        .build()
    );

    if (!reservations.isEmpty()) {
      String message = "해당 일시엔 이미 예약이 존재합니다. 예약 정보 : %s".formatted(reservations.get(0).toString());
      log.debug("예약 생성 중 예외 발생 : {}", message);
      throw new IllegalArgumentException("해당 일시엔 이미 예약이 존재합니다. 예약 정보 : %s".formatted(reservations.get(0).toString()));
    }
  }
}
