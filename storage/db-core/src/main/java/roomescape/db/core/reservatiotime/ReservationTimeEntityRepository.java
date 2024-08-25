package roomescape.db.core.reservatiotime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.core.domain.reservationtime.ReservationTime;
import roomescape.core.domain.reservationtime.ReservationTimeId;
import roomescape.core.domain.reservationtime.ReservationTimeRepository;
import roomescape.core.domain.reservationtime.exception.ReservationTimeNotFoundException;

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
        return jdbcRepository.findById(timeId.value())
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
        jdbcRepository.delete(timeId.value());
    }
}
