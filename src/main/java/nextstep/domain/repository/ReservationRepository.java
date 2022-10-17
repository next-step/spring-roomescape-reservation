package nextstep.domain.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import nextstep.domain.ReservationEntity;

public interface ReservationRepository {

  ReservationEntity save(ReservationEntity reservation);

  List<ReservationEntity> findReservationsByDate(LocalDate date);

  void deleteByDateAndTime(LocalDate date, LocalTime time);

  Optional<ReservationEntity> findReservationsByDateAndTime(LocalDate date, LocalTime time);
}
