package nextstep.domain.reservation.service;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationRepository;
import nextstep.exception.ClientException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        if (repository.findByDateAndTime(date, time).isEmpty()) {
            throw new ClientException("존재하지 않는 예약을 삭제할 수 없습니다.");
        }

        repository.removeByDateAndTime(date, time);
    }

    public List<ReservationResponse> findAllByDate(String date) {
        return repository.findAllByDate(date).stream()
            .map(ReservationResponse::new)
            .collect(Collectors.toList());
    }
}
