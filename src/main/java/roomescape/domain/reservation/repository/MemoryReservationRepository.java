package roomescape.domain.reservation.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.domain.model.Reservation;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final AtomicLong reservationIdIndex = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public Long save(final Reservation reservation) {
        if (Objects.nonNull(reservation.getId())) {
            this.reservations.remove(reservation);
            this.reservations.add(reservation);
            return reservation.getId();
        }

        return saveNewReservation(reservation);
    }

    private long saveNewReservation(final Reservation reservation) {
        final long reservationId = this.reservationIdIndex.incrementAndGet();
        setReservationId(reservation, reservationId);

        this.reservations.add(reservation);

        return reservationId;
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(this.reservations);
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return this.reservations.stream()
                .filter(reservation -> reservation.matchesId(reservationId))
                .findFirst();
    }

    public void clearAll() {
        this.reservations.clear();
    }

    private void setReservationId(final Reservation reservation, final long reservationId) {
        try {
            final Field idField = Arrays.stream(Reservation.class.getDeclaredFields())
                    .filter(field -> field.getName().equals("id"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Cannot find field named 'id' from Reservation class"));

            idField.setAccessible(true);
            idField.set(reservation, reservationId);
        } catch (IllegalAccessException e) {
            log.error("Error occurred while setting reservation id=%d".formatted(reservationId), e);
        }
    }

}
