package nextstep;

import nextsetp.domain.reservation.Reservation;
import nextsetp.domain.reservation.ReservationRepository;
import nextsetp.domain.reservation.exception.DuplicationReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void save(String date, String time, String name) {
        reservationRepository.findBy(date, time).ifPresent((reservation -> {
            throw new DuplicationReservationException();
        }));

        Reservation reservation = new Reservation(
                LocalDate.parse(date),
                LocalTime.parse(time + ":00"),
                name);

        reservationRepository.save(reservation);
    }

    public List<Reservation> findAllBy(String date) {
        return reservationRepository.findAllBy(date);
    }

    public void delete(String date, String time) {
        reservationRepository.delete(date, time);
    }
}
