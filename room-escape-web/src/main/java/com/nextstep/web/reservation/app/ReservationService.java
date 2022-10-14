package com.nextstep.web.reservation.app;

import com.nextstep.web.reservation.dto.CreateReservationRequest;
import com.nextstep.web.reservation.dto.ReservationResponse;
import nextsetp.domain.reservation.Reservation;
import nextsetp.domain.reservation.ReservationRepository;
import nextsetp.repository.reservation.InmemoryReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository = new InmemoryReservationRepository();

    public List<ReservationResponse> findAllBy(String date) {
        final List<Reservation> reservations = reservationRepository.findAllBy(date);
        return ReservationResponse.toList(reservations);
    }

    public Long save(CreateReservationRequest request) {
        Reservation reservation = new Reservation(request.getDate(), request.getTime(), request.getName());
        return reservationRepository.save(reservation);
    }

    public void delete(String date, String time) {
        reservationRepository.delete(date, time);
    }
}
