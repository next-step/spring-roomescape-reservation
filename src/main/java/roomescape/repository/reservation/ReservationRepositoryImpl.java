package roomescape.repository.reservation;

import org.springframework.stereotype.Repository;
import roomescape.application.dto.command.CreateReservationCommand;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAllReservations() {
        return reservations;
    }

    @Override
    public Reservation createReservation(CreateReservationCommand command) {
        Reservation reservation = Reservation.create(index.getAndIncrement(), command);
        reservations.add(reservation);

        return reservation;
    }
}
