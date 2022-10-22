package nextstep.domain.reservation.service;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationRepository;
import nextstep.exception.ClientException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public Long create(Reservation reservation) {
        if (repository.findByDateAndTime(reservation.getDate().toString(), reservation.getTime().toString()).isPresent()) {
            throw new ClientException("해당 날짜와 시간에 이미 예약이 존재합니다.");
        }

        return repository.create(reservation);
    }

    public void removeByDateAndTime(String date, String time) {
        repository.removeByDateAndTime(date, time);
    }

    public List<Reservation> findAllByDate(String date) {
        return repository.findAllByDate(date);
    }
}
