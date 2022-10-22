package nextsetp.domain.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InmemoryReservationRepository implements ReservationRepository {
    List<Reservation> reservations = new ArrayList<>();

    @Override
    public Long save(Reservation reservation) {
        reservations.add(reservation);
        return (long) (reservations.size());
    }

    @Override
    public Optional<Reservation> findByScheduleId(Long scheduleId) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getScheduleId(), scheduleId))
                .findFirst();
    }

    @Override
    public List<Reservation> findAllBy(String date) {
        return null;
    }

    @Override
    public void delete(Long id) {
        reservations.remove(id);
    }
}
