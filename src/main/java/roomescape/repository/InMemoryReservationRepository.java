package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.repository.entity.ReservationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final AtomicLong index = new AtomicLong(0);
    private List<ReservationEntity> reservationEntities = new ArrayList<>();

    public ReservationEntity save(ReservationEntity reservationEntity) {
        if (!reservationEntity.isSaved()) {
            ReservationEntity savedEntity = reservationEntity.changeId(index.incrementAndGet());
            reservationEntities.add(savedEntity);

            return savedEntity;
        }

        reservationEntities = reservationEntities.stream()
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
        reservationEntities = reservationEntities.stream()
                .filter(reservationEntity -> !reservationEntity.isSameId(reservationId))
                .collect(Collectors.toList());
    }

    @Override
    public ReservationEntity findById(Long id) {
        return reservationEntities.stream()
                .filter(reservationEntity -> reservationEntity.isSameId(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(
                                "[id:%d] id에 해당하는 reservation 엔티티가 존재하지 않습니다.", id
                        )
                ));
    }
}
