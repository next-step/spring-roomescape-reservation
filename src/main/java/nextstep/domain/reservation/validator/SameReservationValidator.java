package nextstep.domain.reservation.validator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nextstep.domain.reservation.ReservationFinder;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import nextstep.domain.schedule.ScheduleRepository;
import nextstep.exception.ReservationIllegalArgumentException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SameReservationValidator implements ReservationCreateValidator {

  ReservationFinder reservationFinder;
  ScheduleRepository scheduleRepository;

  @Override
  public void validate(Create createReq) {
    checkExistSchedule(createReq);
    var reservations = reservationFinder.findAll(ReservationFindCondition.builder()
        .scheduleId(createReq.scheduleId())
        .build()
    );

    if (!reservations.isEmpty()) {
      String message = "해당 일시엔 이미 예약이 존재합니다. 예약 정보 : %s".formatted(reservations.get(0).toString());
      log.debug("예약 생성 중 예외 발생 : {}", message);
      throw new ReservationIllegalArgumentException(
          "해당 일시엔 이미 예약이 존재합니다. 예약 정보 : %s".formatted(reservations.get(0).toString()));
    }
  }

  private void checkExistSchedule(Create createReq) {
    scheduleRepository.findById(createReq.scheduleId())
        .orElseThrow(() -> new ReservationIllegalArgumentException(
            "스케줄 ID에 해당하는 스케줄이 없습니다. %d".formatted(createReq.scheduleId()))
        );
  }
}
