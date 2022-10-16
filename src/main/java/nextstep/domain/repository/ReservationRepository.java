package nextstep.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import nextstep.domain.ReservationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

  private static final Map<Long, ReservationEntity> STORE = new ConcurrentHashMap<>();
  private static AtomicLong sequence = new AtomicLong(1L);

  public ReservationEntity save(ReservationEntity reservation) {
    var sequenceId = sequence.getAndIncrement();
    var entity = reservation.toBuilder()
        .id(sequenceId)
        .build();
    STORE.put(sequenceId, entity);
    return entity;
  }

  public List<ReservationEntity> findReservationsByDate(LocalDate date) {
    return STORE.values().stream()
        .filter(it -> it.getDate().isEqual(date))
        .toList();
  }
}
