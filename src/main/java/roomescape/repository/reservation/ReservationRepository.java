package roomescape.repository.reservation;

import org.springframework.stereotype.Repository;
import roomescape.application.dto.command.CreateReservationCommand;
import roomescape.domain.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository {
    public List<Reservation> findAllReservations();
    public Reservation createReservation(CreateReservationCommand command);
    public void deleteReservation(Long id);
}
