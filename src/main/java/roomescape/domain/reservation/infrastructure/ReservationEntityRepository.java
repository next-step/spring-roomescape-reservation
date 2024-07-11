package roomescape.domain.reservation.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.application.ReservationRepository;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.domain.ReservationDate;
import roomescape.domain.reservation.domain.ReservationGuestName;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.exception.ReservationNotFoundException;
import roomescape.domain.reservationtime.domain.ReservationTimeId;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationEntityRepository implements ReservationRepository {

    private final ReservationJdbcRepository jdbcRepository;

    @Override
    public Reservation save(final Reservation reservation) {
        final ReservationEntity saved = jdbcRepository.save(ReservationEntity.fromModel(reservation));
        return saved.toModel();
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcRepository.findAll().stream()
                .map(ReservationEntity::toModel)
                .toList();
    }

    @Override
    public Reservation getById(final Long reservationId) {
        return jdbcRepository.findById(reservationId)
                .map(ReservationEntity::toModel)
                .orElseThrow(() -> ReservationNotFoundException.from(new ReservationId(reservationId)));
    }

    @Override
    public Optional<Reservation> findBy(
            final ReservationGuestName name,
            final ReservationDate date,
            final ReservationTimeId timeId
    ) {
        return jdbcRepository.findBy(name, date, timeId).map(ReservationEntity::toModel);
    }

    @Override
    public void deleteAllInBatch() {
        jdbcRepository.deleteAllInBatch();
    }

    @Override
    public List<Reservation> findAllByTimeId(final ReservationTimeId timeId) {
        return jdbcRepository.findAllByTimeId(timeId).stream()
                .map(ReservationEntity::toModel)
                .toList();
    }
}
