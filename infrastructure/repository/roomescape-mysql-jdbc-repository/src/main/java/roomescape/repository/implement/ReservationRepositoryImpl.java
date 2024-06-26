package roomescape.repository.implement;

import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationRepository;
import roomescape.domain.reservation.ReservationViews;
import roomescape.error.exception.NotFoundDomainException;
import roomescape.repository.ReservationJdbcRepository;
import roomescape.repository.entity.ReservationEntity;
import roomescape.repository.projection.ReservationViewProjection;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJdbcRepository repository;

    public ReservationRepositoryImpl(ReservationJdbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return repository.save(ReservationEntity.from(reservation))
                .toDomain();
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public Reservation findById(Long reservationId) {
        return repository.findById(reservationId)
                .orElseThrow(() -> NotFoundDomainException.notFoundReservation(reservationId))
                .toDomain();
    }

    @Override
    public ReservationViews findAllReservationViews() {
        return new ReservationViews(
                repository.findAllReservationViewProjection().stream()
                        .map(ReservationViewProjection::toDomain)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public boolean existByTimeId(Long timeId) {
        return repository.findByTimeId(timeId).isPresent();
    }

    @Override
    public boolean existByDateAndTimeId(LocalDate date, Long timeId) {
        return repository.findByDateAndTimeId(date, timeId).isPresent();
    }
}
