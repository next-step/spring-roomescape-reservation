package roomescape.repositories;

import org.springframework.stereotype.Repository;
import roomescape.entities.Reservation;

@Repository
public interface ReservationRepository implements BaseRepository<Reservation, Long>{

}
