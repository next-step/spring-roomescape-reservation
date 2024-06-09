package roomescape.domain.reservation.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.dto.ReservationSearchKey;
import roomescape.domain.reservation.exception.ReservationNotFoundException;
import roomescape.domain.reservation.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationReader {

    private final ReservationRepository repository;

    public Reservation getById(final ReservationId id) {
        return findById(id).orElseThrow(
                () -> new ReservationNotFoundException("Cannot find Reservation matching id=%d".formatted(id.value()))
        );
    }

    public List<Reservation> findAll() {
        return repository.findAll();
    }

    public boolean activeReservationExists(final ReservationSearchKey key) {
        return findActiveReservationBy(key).isPresent();
    }

    public Reservation getActiveReservationBy(final ReservationSearchKey key) {
        return findActiveReservationBy(key).orElseThrow(() ->
                new ReservationNotFoundException("Cannot find Reservation for key=%s".formatted(key.toString()))
        );
    }

    private Optional<Reservation> findById(final ReservationId id) {
        return repository.findById(id.value());
    }

    private Optional<Reservation> findActiveReservationBy(ReservationSearchKey key) {
        return findAll().stream()
                .filter(reservation -> matchesKey(key, reservation))
                .filter(Reservation::isActive)
                .findFirst();
    }

    private boolean matchesKey(final ReservationSearchKey key, final Reservation reservation) {
        return reservation.matchesName(key.getName()) && reservation.matchesTimestamp(key.getTimeStamp());
    }

}
