package nextstep.application.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.dto.Reservation;
import nextstep.application.reservation.dto.ReservationDeleteValidationDto;
import nextstep.application.reservation.dto.ReservationRes;
import nextstep.application.schedule.ScheduleService;
import nextstep.domain.reservation.ReservationEntity;
import nextstep.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

  private final ScheduleService scheduleService;

  private final ReservationRepository repository;

  private final ReservationCreatePolicy createPolicy;
  private final ReservationDeletePolicy deletePolicy;

  @Transactional
  public Long create(Reservation req) {
    createPolicy.checkValid(req);
    var reservation = ReservationEntity.builder()
        .scheduleId(req.scheduleId())
        .themeId(req.themeId())
        .date(req.date())
        .time(req.time())
        .name(req.name())
        .build();
    var entity = repository.save(reservation);
    return entity.getId();
  }

  public List<ReservationRes> findReservations(LocalDate date) {
    var reservations = repository.findReservationsByDate(date);
    return reservations.stream()
        .map(it -> ReservationRes.builder()
            .id(it.getId())
            .schedule(scheduleService.getSchedule(it.getScheduleId()).orElseThrow(
                () -> new IllegalArgumentException(String.format("스케쥴을 찾을 수 없습니다. 스케쥴ID: %s", it.getScheduleId())))
            )
            .date(it.getDate())
            .time(it.getTime())
            .name(it.getName())
            .build())
        .toList();
  }

  @Transactional
  public void removeReservation(LocalDate date, LocalTime time) {
    deletePolicy.checkValid(ReservationDeleteValidationDto.builder()
        .date(date)
        .time(time)
        .build());
    repository.deleteByDateAndTime(date, time);
  }

}
