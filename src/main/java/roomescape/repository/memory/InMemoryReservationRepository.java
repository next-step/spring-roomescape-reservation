package roomescape.repository.memory;

import org.springframework.stereotype.Repository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.entity.ReservationEntity;
import roomescape.repository.projection.ReservationViewProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final AtomicLong index = new AtomicLong(0);
    private List<ReservationEntity> reservationEntities = new ArrayList<>();

    public ReservationEntity save(ReservationEntity reservationEntity) {
        if (!this.containSameId(reservationEntity.getId())) {
            ReservationEntity savedEntity = reservationEntity.changeId(index.incrementAndGet());
            this.reservationEntities.add(savedEntity);

            return savedEntity;
        }

        this.reservationEntities = this.reservationEntities.stream()
                .map(reservation -> {
                    if (reservationEntity.isSameId(reservationEntity)) {
                        return reservationEntity;
                    }

                    return reservation;
                }).collect(Collectors.toList());

        return reservationEntity;
    }

    @Override
    public List<ReservationEntity> findAll() {
        return List.copyOf(this.reservationEntities);
    }

    @Override
    public void delete(Long reservationId) {
        this.reservationEntities = this.reservationEntities.stream()
                .filter(reservationEntity -> !reservationEntity.isSameId(reservationId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationEntity> findById(Long reservationId) {
        return this.reservationEntities.stream()
                .filter(reservationEntity -> reservationEntity.isSameId(reservationId))
                .findAny();
    }

    @Override
    public List<ReservationViewProjection> findAllReservationViewProjection() {
        // 미구현
        return null;
    }

    @Override
    public Optional<ReservationEntity> findByTimeId(Long timeId) {
        // 미구현
        return Optional.empty();
    }

    private boolean containSameId(Long id) {
        return this.reservationEntities.stream()
                .anyMatch(reservationEntity -> reservationEntity.isSameId(id));
    }
}
