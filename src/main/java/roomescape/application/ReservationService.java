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
        Reservation reservation = Reservation.createReservationWithoutId(input.getName(), input.getDate(),
            input.getTime());
        long id = reservationRepository.save(reservation);
        Reservation saved = reservationRepository.findById(id).get();
        return createReservationServiceOutput(saved);
    }

    @Transactional(readOnly = true)
    public List<ReservationServiceOutput> retrieveReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(ReservationServiceOutput::createReservationServiceOutput).toList();
    }

    @Transactional
    public void delete(Long id) {
        if (reservationRepository.findById(id).isEmpty()) {
            throw new NotFoundException("이미 삭제된 예약입니다.");
        }
        reservationRepository.deleteById(id);
    }
}
