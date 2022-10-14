package nextsetp.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
     Long save(Reservation reservation);
     List<Reservation> findAllBy(String date);
     void delete(String date, String time);
}
