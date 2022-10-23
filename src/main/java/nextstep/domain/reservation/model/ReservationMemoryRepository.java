package nextstep.domain.reservation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReservationMemoryRepository implements ReservationRepository {
    private static final List<Reservation> reservations = new ArrayList<>();

    @Override
    public Long create(Reservation reservation) {
        Reservation reservationWithId = reservation.withId();

        reservations.add(reservation);

        return reservationWithId.getId();
    }

    @Override
    public Optional<Reservation> findByScheduleId(Long scheduleId) {
        return reservations.stream()
            .filter(it -> Objects.equals(it.getScheduleId(), scheduleId))
            .findFirst();
    }

    @Override
    public List<Reservation> findAllByScheduledIds(List<Long> scheduleIds) {
        return reservations.stream()
            .filter(it -> scheduleIds.contains(it.getScheduleId()))
            .collect(Collectors.toList());
    }

    @Override
    public void removeByScheduleId(Long scheduleId) {
        findByScheduleId(scheduleId).ifPresent(reservations::remove);
    }
}
