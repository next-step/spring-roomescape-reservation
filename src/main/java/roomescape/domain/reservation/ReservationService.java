package roomescape.domain.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.infra.reservation.ReservationRepository;

@Service
public class ReservationService {
  private final ReservationRepository repository;

  public ReservationService(ReservationRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Reservation create(CreateReservation reservation) {
    long savedId = repository.save(reservation);
    return repository.getById(savedId);
  }

  public List<Reservation> findAll() {
    return repository.findAll();
  }

  @Transactional
  public void delete(long id) {
    Reservation byId = repository.getById(id);
    if (byId == null) throw new ReservationNotFound();
    repository.delete(id);
  }
}
