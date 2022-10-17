package nextstep.domain.reservation.service;

import nextstep.domain.reservation.exception.ExistReservationException;
import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationDomainService {
    private final ReservationRepository reservationRepository;

    public ReservationDomainService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation create(LocalDate date, LocalTime time, String name) {
        if (reservationRepository.existByDateTime(date, time)) {
            throw new ExistReservationException(String.format("해당 일시에 이미 예약이 존재합니다. date: %s, time: %s", date, time));
        }

        return reservationRepository.save(new Reservation(date, time, name));
    }

    @Transactional
    public void cancelByDateTime(LocalDate date, LocalTime time) {
        reservationRepository.deleteByDateTime(date, time);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllByDate(LocalDate date) {
        return reservationRepository.findAllByDate(date);
    }
}
