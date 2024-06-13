package roomescape.repository.reservation;

import org.springframework.stereotype.Repository;
import roomescape.application.dto.command.CreateReservationCommand;
import roomescape.domain.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {


    private Map<Long, Reservation> reservations = new HashMap<>();
    private AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAllReservations() {
        return reservations.values().stream().toList();
    }

    @Override
    public Reservation createReservation(CreateReservationCommand command) {
        Reservation reservation = Reservation.create(index.getAndIncrement(), command);
        reservations.put(reservation.getId(), reservation);

        return reservation;
    }

    @Override
    public void deleteReservation(Long id) {
        if(reservations.containsKey(id)){
            reservations.remove(id);
        }
    }
}
