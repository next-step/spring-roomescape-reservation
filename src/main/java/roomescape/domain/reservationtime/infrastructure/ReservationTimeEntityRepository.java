package roomescape.domain.reservationtime.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservationtime.application.ReservationTimeRepository;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.domain.reservationtime.exception.ReservationTimeNotFoundException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationTimeEntityRepository implements ReservationTimeRepository {

    private final ReservationTimeJdbcRepository jdbcRepository;

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        final ReservationTimeEntity timeEntity = ReservationTimeEntity.fromModel(reservationTime);
        final ReservationTimeEntity saved = jdbcRepository.save(timeEntity);
        return saved.toModel();
    }

    @Override
    public ReservationTime getById(final ReservationTimeId timeId) {
        return jdbcRepository.findById(timeId.getValue())
                .map(ReservationTimeEntity::toModel)
                .orElseThrow(() -> ReservationTimeNotFoundException.fromId(timeId));
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcRepository.findAll().stream()
                .map(ReservationTimeEntity::toModel)
                .toList();
    }

    @Override
    public void deleteAllInBatch() {
        jdbcRepository.deleteAllInBatch();
    }

    @Override
    public Optional<ReservationTime> findByStartAt(final LocalTime startAt) {
        return jdbcRepository.findByStartAt(startAt).map(ReservationTimeEntity::toModel);
    }

    @Override
    public ReservationTime getByStartAt(final LocalTime startAt) {
        return jdbcRepository.findByStartAt(startAt)
                .map(ReservationTimeEntity::toModel)
                .orElseThrow(() -> ReservationTimeNotFoundException.fromStartAt(startAt));
    }

    @Override
    public void delete(final ReservationTimeId timeId) {
        jdbcRepository.delete(timeId.getValue());
    }
}
