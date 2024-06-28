package roomescape.infra.time;

import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.time.CreateReservationTime;
import roomescape.domain.time.ReservationTime;

@Repository
public class ReservationTimeRepository {

  private final ReservationTimeJdbcRepository jdbcRepository;


  public ReservationTimeRepository(ReservationTimeJdbcRepository jdbcRepository) {
    this.jdbcRepository = jdbcRepository;

  }

  public List<ReservationTime> findByIds(Collection<Long> ids) {
    return jdbcRepository.findByIds(ids).stream().map(ReservationTimeEntity::toDomain).toList();
  }

  public boolean isUsed(ReservationTime time) {
    return jdbcRepository.isUsed(time);
  }

  public List<ReservationTime> findAll() {
    return jdbcRepository.findAll().stream().map(ReservationTimeEntity::toDomain).toList();
  }

  public ReservationTime findById(long id) {
    return jdbcRepository.findById(id).toDomain();
  }

  public void deleteById(long id) {
    jdbcRepository.deleteById(id);
  }

  @Transactional
  public ReservationTime save(CreateReservationTime newReservationTime) {
    long savedId = jdbcRepository.save(newReservationTime);
    return findById(savedId);
  }
}
