package nextstep.domain.schedule.validator;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.reservation.ReservationFinder;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import nextstep.domain.schedule.Schedule;
import nextstep.domain.schedule.ScheduleRepository;
import nextstep.domain.schedule.dto.ScheduleCommandDto;
import nextstep.exception.ReservationIllegalArgumentException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservedScheduleValidator implements
    ScheduleDeleteValidator {

  ReservationFinder reservationFinder;
  ScheduleRepository scheduleRepository;

  @Override
  public void validate(ScheduleCommandDto.Delete deleteReq) {
    Schedule schedule = scheduleRepository.findById(deleteReq.id())
        .orElseThrow(() ->
            new ReservationIllegalArgumentException("존재하지 않는 예약입니다. 입력된 id : %s".formatted(deleteReq.id()))
        );

    List<Reservation> reservedSchedules = findReservationBySchedule(schedule);

    if (!reservedSchedules.isEmpty()) {
      String reservedScheduleIds = reservedSchedules.stream()
          .map(Reservation::getSchedule)
          .map(Schedule::getId)
          .map(String::valueOf)
          .collect(Collectors.joining(","));

      throw new ReservationIllegalArgumentException("예약되어있는 예약이 있습니다. ID : %s".formatted(reservedScheduleIds));
    }
  }

  private List<Reservation> findReservationBySchedule(Schedule schedule) {
    return reservationFinder.findAll(
        ReservationFindCondition.builder()
            .scheduleId(schedule.getId())
            .build()
    );
  }
}
