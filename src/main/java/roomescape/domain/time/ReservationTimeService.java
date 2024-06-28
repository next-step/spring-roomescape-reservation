package roomescape.domain.time;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.infra.time.ReservationTimeRepository;

@Service
public class ReservationTimeService {

  private final ReservationTimeRepository repository;

  public ReservationTimeService(ReservationTimeRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public ReservationTime create(CreateReservationTime time) {
    return repository.save(time);
  }

  public List<ReservationTime> findAll() {
    return repository.findAll();
  }

  public ReservationTime findOne(long id) {
    ReservationTime byId = repository.findById(id);
    if (byId == null) {
      throw new ReservationTimeNotFound(id);
    }
    return byId;
  }

  @Transactional
  public void delete(long id) {
    ReservationTime byId = repository.findById(id);
    if (byId == null) {
      throw new ReservationTimeNotFound(id);
    }
    if (repository.isUsed(byId)) {
      throw new ReservationTimeIsUsed(byId);
    }
    repository.deleteById(id);
  }
}
