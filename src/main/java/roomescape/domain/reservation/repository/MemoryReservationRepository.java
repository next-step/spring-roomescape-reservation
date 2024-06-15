package roomescape.domain.reservation.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationTimeStamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final AtomicLong reservationIdIndex = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public Reservation save(final Reservation reservation) {
        if (Objects.nonNull(reservation.getId())) {
            this.reservations.remove(reservation);
            this.reservations.add(reservation);
            return reservation;
        }

        return injectId(reservation);
    }

    private Reservation injectId(final Reservation reservation) {
        final Reservation idInjected = Reservation.builder()
                .id(this.reservationIdIndex.incrementAndGet())
                .name(reservation.getName())
                .timeStamp(reservation.getTimeStamp())
                .status(reservation.getStatus())
                .canceledAt(reservation.getCanceledAt())
                .createdAt(reservation.getCreatedAt())
                .build();

        this.reservations.add(idInjected);

        return idInjected;
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

    @Override
    public Optional<Reservation> findByNameAndTimestamp(
            final ReservationGuestName name,
            final ReservationTimeStamp timestamp
    ) {
        return this.reservations.stream()
                .filter(reservation -> reservation.matchesName(name))
                .filter(reservation -> reservation.matchesTimestamp(timestamp))
                .findFirst();
    }

    public void clearAll() {
        this.reservations.clear();
    }
}
