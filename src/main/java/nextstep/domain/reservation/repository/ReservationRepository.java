package nextstep.domain.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import nextstep.domain.reservation.ReservationEntity;

public interface ReservationRepository {

  ReservationEntity save(ReservationEntity reservation);

  List<ReservationEntity> findReservationsByDate(LocalDate date);

  void deleteByDateAndTime(LocalDate date, LocalTime time);

  Optional<ReservationEntity> findReservationsByDateAndTime(LocalDate date, LocalTime time);

  Optional<ReservationEntity> getReservation(Long id);

  Optional<ReservationEntity> findReservationByThemeId(Long themeId);

  Optional<ReservationEntity> findReservationByScheduleId(Long scheduleId);
}
