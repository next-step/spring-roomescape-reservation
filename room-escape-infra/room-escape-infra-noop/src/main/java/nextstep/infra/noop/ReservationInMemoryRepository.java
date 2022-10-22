package nextstep.infra.noop;

import nextstep.core.reservation.Reservation;
import nextstep.core.reservation.out.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {
    private static final Map<Long, Reservation> RESERVATIONS = new ConcurrentHashMap<>();
    private final AtomicLong incrementor = new AtomicLong(1L);

    @Override
    public Reservation save(Reservation reservation) {
        Objects.requireNonNull(reservation);

        Reservation data = new Reservation(incrementor.getAndIncrement(), reservation.getDate(), reservation.getTime(), reservation.getName());
        RESERVATIONS.put(data.getId(), data);
        return reservation;
    }

    @Override
    public List<Reservation> findAllByDate(LocalDate date) {
        Objects.requireNonNull(date);

        return RESERVATIONS.values().stream()
                .filter(it -> it.isSameDate(date))
                .toList();
    }

    @Override
    public void deleteByDateAndTime(Long scheduleId, LocalDate date, LocalTime time) {
        Objects.requireNonNull(scheduleId);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);

        RESERVATIONS.values().stream()
                .filter(it -> it.isSameScheduleId(scheduleId) && it.isSameDate(date) && it.isSameTime(time))
                .map(Reservation::getId)
                .forEach(RESERVATIONS::remove);
    }

    @Override
    public boolean existsByScheduleIdAndDateAndTime(Long scheduleId, LocalDate date, LocalTime time) {
        Objects.requireNonNull(scheduleId);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);

        Optional<Reservation> reservation = RESERVATIONS.values().stream()
                .filter(it -> it.isSameScheduleId(scheduleId) && it.isSameDate(date) && it.isSameTime(time))
                .findFirst();
        return reservation.isPresent();
    }
}
