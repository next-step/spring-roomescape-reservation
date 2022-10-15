package nextsetp.domain.reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
     Long save(Reservation reservation);
     Optional<Reservation> findBy(String date, String time);
     List<Reservation> findAllBy(String date);
     void delete(String date, String time);
}
