package nextstep.app.reservation;

import nextsetp.domain.reservation.Reservation;
import nextsetp.domain.reservation.ReservationRepository;
import nextsetp.domain.reservation.exception.DuplicationReservationException;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void save(Long scheduleId, String name) {
        reservationRepository.findByScheduleId(scheduleId).ifPresent((reservation -> {
            throw new DuplicationReservationException();
        }));

        Reservation reservation = new Reservation(
                scheduleId,
                LocalDateTime.now(),
                name);

        reservationRepository.save(reservation);
    }

    public List<Reservation> findAllBy(String date) {
        return reservationRepository.findAllBy(date);
    }

    public void delete(Long reservationId) {
        reservationRepository.delete(reservationId);
    }
}
