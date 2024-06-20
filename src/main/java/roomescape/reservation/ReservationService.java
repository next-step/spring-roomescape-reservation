package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.entities.Reservation;
import roomescape.errors.ErrorCode;
import roomescape.exceptions.SpringRoomException;
import roomescape.repositories.ReservationRepository;
import roomescape.reservation.data.ReservationSearchResponse;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation saveReservation(Reservation reservation) {
        Optional<Reservation> existingReservation = reservationRepository.findByDateAndTime(reservation.getDate(), reservation.getReservationTime().getTime());
        if (existingReservation.isPresent()) {
            throw new SpringRoomException(ErrorCode.INVALID_INPUT_VALUE, "이미 예약된 시간입니다.");
        }
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
