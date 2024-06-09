package roomescape.apply.reservationtime.application;

import org.springframework.stereotype.Service;
import roomescape.apply.reservation.application.ReservationFinder;
import roomescape.apply.reservationtime.application.exception.ReservationTimeReferencedException;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeRepository;
import roomescape.apply.reservationtime.application.exception.NotFoundReservationTimeException;

@Service
public class ReservationTimeDeleter {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationFinder reservationFinder;

    public ReservationTimeDeleter(ReservationTimeRepository reservationTimeRepository, ReservationFinder reservationFinder) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationFinder = reservationFinder;
    }

    public void deleteReservationTimeBy(long id) {
        final long existId = reservationTimeRepository.checkIdExists(id).orElseThrow(NotFoundReservationTimeException::new);
        boolean isReferenced = reservationFinder.findAnyByTimeId(id).isPresent();
        if (isReferenced) {
            throw new ReservationTimeReferencedException();
        }

        reservationTimeRepository.deleteById(existId);
    }
}
