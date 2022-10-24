package nextstep.application.reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.dto.ReservationRes;
import nextstep.application.schedule.ScheduleQueryService;
import nextstep.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryService {

  private final ScheduleQueryService scheduleQueryService;

  private final ReservationRepository repository;

  public List<ReservationRes> findReservations(LocalDate date) {
    var reservations = repository.findReservationsByDate(date);
    return reservations.stream()
        .map(it -> ReservationRes.builder()
            .id(it.getId())
            .schedule(scheduleQueryService.getSchedule(it.getScheduleId()).orElseThrow(
                () -> new IllegalArgumentException(String.format("스케쥴을 찾을 수 없습니다. 스케쥴ID: %s", it.getScheduleId())))
            )
            .date(it.getDate())
            .time(it.getTime())
            .name(it.getName())
            .build())
        .toList();
  }

  public Optional<ReservationRes> getReservationByThemeId(Long themeId) {
    var entity = repository.findReservationByThemeId(themeId);
    if (entity.isPresent()) {
      var reservation = entity.get();
      return Optional.ofNullable(ReservationRes.builder()
          .id(reservation.getId())
          .schedule(scheduleQueryService.getSchedule(reservation.getScheduleId()).orElseThrow(
              () -> new IllegalArgumentException(
                  String.format("스케쥴을 찾을 수 없습니다. 스케쥴ID: %s", reservation.getScheduleId())))
          )
          .date(reservation.getDate())
          .time(reservation.getTime())
          .name(reservation.getName())
          .build());

    }
    return Optional.empty();
  }

  public Optional<ReservationRes> getReservationByScheduleId(Long scheduleId) {
    var entity = repository.findReservationByScheduleId(scheduleId);
    if (entity.isPresent()) {
      var reservation = entity.get();
      return Optional.ofNullable(ReservationRes.builder()
          .id(reservation.getId())
          .schedule(scheduleQueryService.getSchedule(reservation.getScheduleId()).orElseThrow(
              () -> new IllegalArgumentException(
                  String.format("스케쥴을 찾을 수 없습니다. 스케쥴ID: %s", reservation.getScheduleId())))
          )
          .date(reservation.getDate())
          .time(reservation.getTime())
          .name(reservation.getName())
          .build());

    }
    return Optional.empty();
  }
}
