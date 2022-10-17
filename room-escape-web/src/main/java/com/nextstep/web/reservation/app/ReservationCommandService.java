package com.nextstep.web.reservation.app;

import com.nextstep.web.reservation.dto.CreateReservationRequest;
import nextsetp.domain.reservation.Reservation;
import nextsetp.domain.reservation.ReservationRepository;
import nextsetp.domain.reservation.exception.DuplicationReservationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationCommandService {
    private final ReservationRepository reservationRepository;

    public ReservationCommandService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long save(CreateReservationRequest request) {
        reservationRepository.findBy(request.getDate().toString(), request.getTime().toString()).ifPresent((reservation -> {
            throw new DuplicationReservationException();
        }));

        Reservation reservation = new Reservation(request.getDate(), request.getTime(), request.getName());
        return reservationRepository.save(reservation);
    }

    public void delete(String date, String time) {
        reservationRepository.delete(date, time);
    }
}
