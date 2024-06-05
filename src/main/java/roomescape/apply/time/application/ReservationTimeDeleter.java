package roomescape.apply.time.application;

import org.springframework.stereotype.Service;
import roomescape.apply.time.domain.repository.ReservationTimeRepository;

import java.util.NoSuchElementException;

@Service
public class ReservationTimeDeleter {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeDeleter(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void deleteReservationTimeBy(long id) {
        final long existId = reservationTimeRepository.checkIdExists(id).orElseThrow(NoSuchElementException::new);
        reservationTimeRepository.delete(existId);
    }
}
