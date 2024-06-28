package roomescape.domain.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.ReservationTimeService;
import roomescape.infra.reservation.ReservationRepository;

@Service
public class ReservationService {

  private final ReservationRepository repository;
  private final ReservationTimeService timeService;

  public ReservationService(ReservationRepository repository, ReservationTimeService timeService) {
    this.repository = repository;
    this.timeService = timeService;
  }

  @Transactional
  public Reservation create(CreateReservation reservation) {
    ReservationTime time = timeService.findOne(reservation.timeId());
    time.validDateTime(reservation.date());
    if (repository.isExists(reservation)) {
      throw new ReservationIsExists(reservation.date(), time);
    }
    long savedId = repository.save(reservation);
    return repository.getById(savedId);
  }



  public List<Reservation> findAll() {
    return repository.findAll();
  }

  @Transactional
  public void delete(long id) {
    Reservation byId = repository.getById(id);
    if (byId == null) {
      throw new ReservationNotFound(id);
    }
    repository.delete(id);
  }
}
