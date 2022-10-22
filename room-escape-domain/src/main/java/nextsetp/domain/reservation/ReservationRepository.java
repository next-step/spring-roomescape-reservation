package nextsetp.domain.reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
     Long save(Reservation reservation);
     Optional<Reservation> findByScheduleId(Long id);
     List<Reservation> findAllBy(String date);
     void delete(Long id);
}
