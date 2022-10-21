package nextstep.app.web.reservation.adapter.in;

import nextstep.core.reservation.Reservation;
import nextstep.core.reservation.in.ReservationCreateRequest;
import nextstep.core.reservation.in.ReservationResponse;
import nextstep.core.reservation.in.ReservationUseCase;
import nextstep.core.reservation.out.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService implements ReservationUseCase {
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ReservationResponse create(ReservationCreateRequest request) {
        Objects.requireNonNull(request);
        Reservation reservation = request.to();
        validateSameDateAndTime(reservation);

        Reservation saved = repository.save(reservation);
        return ReservationResponse.from(saved);
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
    public List<ReservationResponse> findAllByDate(LocalDate date) {
        Objects.requireNonNull(date);

        return repository.findAllByDate(date)
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional
    public void deleteByDateAndTime(LocalDate date, LocalTime time) {
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);


        repository.deleteByDateAndTime(date, time);
    }
}
