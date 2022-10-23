package nextstep.application.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.dto.Reservation;
import nextstep.application.reservation.dto.ReservationDeleteValidationDto;
import nextstep.application.reservation.dto.ReservationRes;
import nextstep.domain.reservation.ReservationEntity;
import nextstep.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

  private final ReservationQueryService reservationQueryService;

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
    return reservationQueryService.findReservations(date);
  }

  @Transactional
  public void removeReservation(LocalDate date, LocalTime time) {
    deletePolicy.checkValid(ReservationDeleteValidationDto.builder()
        .date(date)
        .time(time)
        .build());
    repository.deleteByDateAndTime(date, time);
  }

  public Optional<ReservationRes> getReservationByThemeId(Long themeId) {
    return reservationQueryService.getReservationByThemeId(themeId);
  }

  public Optional<ReservationRes> getReservationByScheduleId(Long scheduleId) {
    return reservationQueryService.getReservationByScheduleId(scheduleId);
  }
}
