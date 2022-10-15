package nextstep.app.web;

import nextstep.core.Reservation;
import nextstep.core.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {
    private static final Map<Long, Reservation> RESERVATIONS = new ConcurrentHashMap<>();
    private long incrementor = 1;

    @Override
    public Reservation save(Reservation reservation) {
        Objects.requireNonNull(reservation);
        validateSameDateAndTime(reservation);

        reservation.setId(incrementor++);
        RESERVATIONS.put(reservation.getId(), reservation);
        return reservation;
    }

    private void validateSameDateAndTime(Reservation reservation) {
        if (RESERVATIONS.values()
                .stream()
                .anyMatch(saved -> saved.isSameDate(reservation.getDate()) && saved.isSameTime(reservation.getTime()))
        ) {
            throw new RuntimeException("동일한 날짜와 시간엔 예약할 수 없습니다.");
        }
    }

    @Override
    public List<Reservation> findAllByDate(LocalDate date) {
        Objects.requireNonNull(date);

        return RESERVATIONS.values().stream()
                .filter(reservation -> reservation.isSameDate(date))
                .toList();
    }

    @Override
    public void deleteByDateAndTime(LocalDate date, LocalTime time) {
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);

        RESERVATIONS.values().stream()
                .filter(reservation -> reservation.isSameDate(date) && reservation.isSameTime(time))
                .map(Reservation::getId)
                .forEach(RESERVATIONS::remove);
    }
}
