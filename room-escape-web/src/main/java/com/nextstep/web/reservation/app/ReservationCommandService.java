package com.nextstep.web.reservation.app;

import com.nextstep.web.reservation.dto.CreateReservationRequest;
import nextsetp.domain.reservation.Reservation;
import nextsetp.domain.reservation.ReservationRepository;
import nextsetp.domain.reservation.exception.DuplicationReservationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ReservationCommandService {
    private final ReservationRepository reservationRepository;

    public ReservationCommandService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long save(CreateReservationRequest request) {
        reservationRepository.findByScheduleId(request.getScheduleId()).ifPresent((reservation -> {
            throw new DuplicationReservationException();
        }));

        Reservation reservation = new Reservation(request.getScheduleId(), LocalDateTime.now(),
                request.getName());
        return reservationRepository.save(reservation);
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
