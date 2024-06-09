package roomescape.application;

import static roomescape.application.ReservationServiceOutput.createReservationServiceOutput;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.common.exception.NotFoundException;
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public ReservationServiceOutput create(ReservationServiceInput input) {
        Reservation reservation = Reservation.createReservation(input.getName(), input.getDate(),
            input.getTimeId(), input.getStartAt());
        Reservation saved = reservationRepository.save(reservation);
        return createReservationServiceOutput(saved);
    }

    @Transactional(readOnly = true)
    public List<ReservationServiceOutput> retrieveReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(ReservationServiceOutput::createReservationServiceOutput).toList();
    }

    @Transactional
    public void delete(Long id) {
        if (reservationRepository.deleteById(id) > 0) {
            return;
        }
        throw new NotFoundException("이미 삭제된 예약입니다.");
    }
}
