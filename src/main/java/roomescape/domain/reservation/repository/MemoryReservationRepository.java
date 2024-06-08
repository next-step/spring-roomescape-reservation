package roomescape.domain.reservation.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.domain.model.Reservation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final AtomicLong reservationIdIndex = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public Long save(final Reservation reservation) {
        final long reservationId = this.reservationIdIndex.incrementAndGet();
        setReservationId(reservation, reservationId);

        this.reservations.add(reservation); // fixme 같은 Id 가 있을 때 exception or update 처리

        return reservationId;
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(this.reservations);
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
