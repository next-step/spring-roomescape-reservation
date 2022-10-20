package nextstep.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Reservation save(Reservation reservation) {
        Objects.requireNonNull(reservation);
        validateSameDateAndTime(reservation);

        return repository.save(reservation);
    }

    private void validateSameDateAndTime(Reservation reservation) {
        if (repository.findAll()
                .stream()
                .anyMatch(it -> it.isSameDate(reservation.getDate()) && it.isSameTime(reservation.getTime()))
        ) {
            throw new IllegalArgumentException("동일한 날짜와 시간엔 예약할 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllByDate(LocalDate date) {
        return repository.findAllByDate(date);
    }

    @Transactional
    public void deleteByDateAndTime(LocalDate date, LocalTime time) {
        repository.deleteByDateAndTime(date, time);
    }
}
