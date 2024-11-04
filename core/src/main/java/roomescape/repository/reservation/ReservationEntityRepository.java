package roomescape.repository.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.domain.common.ActiveStatus;
import roomescape.domain.reservation.*;
import roomescape.domain.reservation.exception.ReservationNotFoundException;
import roomescape.domain.reservationtime.ReservationTimeId;
import roomescape.domain.theme.ThemeId;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ReservationEntityRepository implements ReservationRepository {

    private final ReservationJdbcRepository jdbcRepository;

    @Override
    public Reservation save(final Reservation reservation) {
        return jdbcRepository.save(reservation);
    }

    @Override
    public List<Reservation> findNotDeletedReservations() {
        return jdbcRepository.findAllByActiveStatus(ActiveStatus.ACTIVE);
    }

    @Override
    public Reservation getById(final Long reservationId) {
        return jdbcRepository.findById(reservationId)
                .orElseThrow(() -> ReservationNotFoundException.from(new ReservationId(reservationId)));
    }

    @Override
    public Optional<Reservation> findBy(
            final ReservationGuestName name,
            final ReservationDate date,
            final ReservationTimeId timeId
    ) {
        return jdbcRepository.findBy(name, date, timeId);
    }

    @Override
    public List<Reservation> findAllByTimeId(final ReservationTimeId timeId) {
        return jdbcRepository.findAllByTimeId(timeId);
    }

    @Override
    public List<Reservation> findAllByThemeId(final ThemeId themeId) {
        return jdbcRepository.findAllByThemeId(themeId);
    }
}
