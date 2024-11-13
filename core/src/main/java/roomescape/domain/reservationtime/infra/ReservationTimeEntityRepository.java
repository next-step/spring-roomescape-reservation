package roomescape.domain.reservationtime.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.domain.reservationtime.domain.ReservationTimeRepository;
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
        return jdbcRepository.save(reservationTime);
    }

    @Override
    public ReservationTime getById(final ReservationTimeId timeId) {
        return jdbcRepository.findById(timeId.value())
                .orElseThrow(() -> ReservationTimeNotFoundException.fromId(timeId));
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcRepository.findAll().stream()
                .toList();
    }

    @Override
    public List<ReservationTime> findAllByIds(final List<ReservationTimeId> reservationTimeIds) {
        final List<Long> timeIdValues = reservationTimeIds.stream().map(ReservationTimeId::value).toList();
        return jdbcRepository.findAllByIds(timeIdValues);
    }

    @Override
    public Optional<ReservationTime> findByStartAt(final LocalTime startAt) {
        return jdbcRepository.findByStartAt(startAt);
    }

    @Override
    public ReservationTime getByStartAt(final LocalTime startAt) {
        return jdbcRepository.findByStartAt(startAt)
                .orElseThrow(() -> ReservationTimeNotFoundException.fromStartAt(startAt));
    }

    @Override
    public void delete(final ReservationTimeId timeId) {
        jdbcRepository.delete(timeId.value());
    }

    @Override
    public void delete(final ReservationTime reservationTime) {
        jdbcRepository.delete(reservationTime.getId().value());
    }
}
