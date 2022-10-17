package nextstep.data.local.reservation;

import nextstep.domain.reservation.domain.model.Reservation;
import nextstep.domain.reservation.domain.model.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationLocalRepository implements ReservationRepository {
    private static final AtomicLong RESERVATION_ID = new AtomicLong(1);
    private static final Map<Long, Reservation> RESERVATIONS = new ConcurrentHashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        return Optional.ofNullable(reservation.id())
                .map(it -> {
                    RESERVATIONS.put(reservation.id(), reservation);
                    return RESERVATIONS.get(it);
                })
                .orElseGet(() -> {
                    long id = RESERVATION_ID.getAndIncrement();
                    RESERVATIONS.put(id, reservation.withId(id));
                    return RESERVATIONS.get(id);
                });
    }

    @Override
    public List<Reservation> findAllByDate(LocalDate date) {
        return RESERVATIONS.values().stream()
                .filter(it -> it.isDateEqual(date))
                .toList();
    }

    @Override
    public void deleteAll() {
        RESERVATIONS.clear();
    }

    @Override
    public void deleteByScheduleIdAndDateAndTime(Long scheduleId, LocalDate date, LocalTime time) {
        RESERVATIONS.values().stream()
                .filter(it -> it.isScheduleEqual(scheduleId) && it.isDateEqual(date) && it.isTimeEqual(time))
                .forEach(it -> RESERVATIONS.remove(it.id()));
    }

    @Override
    public Optional<Reservation> findByScheduleIdAndDateTime(Long scheduleId, LocalDateTime dateTime) {
        return RESERVATIONS.values().stream()
                .filter(it -> it.isScheduleEqual(scheduleId) && it.isDateEqual(dateTime.toLocalDate()) && it.isTimeEqual(dateTime.toLocalTime()))
                .findAny();
    }
}
