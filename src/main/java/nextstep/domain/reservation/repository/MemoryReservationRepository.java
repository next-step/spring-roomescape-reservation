package nextstep.domain.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import nextstep.domain.reservation.ReservationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

  private static final Map<Long, ReservationEntity> STORE = new ConcurrentHashMap<>();
  private static AtomicLong sequence = new AtomicLong(1L);

  @Override
  public ReservationEntity save(ReservationEntity reservation) {
    var sequenceId = sequence.getAndIncrement();
    var entity = reservation.toBuilder()
        .id(sequenceId)
        .build();
    STORE.put(sequenceId, entity);
    return entity;
  }

  @Override
  public List<ReservationEntity> findReservationsByDate(LocalDate date) {
    return STORE.values().stream()
        .filter(it -> it.getDate().isEqual(date))
        .toList();
  }

  @Override
  public void deleteByDateAndTime(LocalDate date, LocalTime time) {
    findReservationsByDateAndTime(date, time).ifPresent(it -> STORE.remove(it.getId()));
  }

  @Override
  public Optional<ReservationEntity> findReservationsByDateAndTime(LocalDate date, LocalTime time) {
    return STORE.values().stream()
        .filter(it -> it.getDate().isEqual(date) && Objects.equals(it.getTime(), time))
        .findFirst();
  }
}
