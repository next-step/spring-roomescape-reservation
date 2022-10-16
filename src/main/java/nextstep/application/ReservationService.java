package nextstep.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import nextstep.domain.Reservation;
import nextstep.domain.repository.ReservationRepository;
import nextstep.exception.ReservationException;
import nextstep.presentation.dto.ReservationRequest;
import nextstep.presentation.dto.ReservationResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Integer make(ReservationRequest request) {
        Optional<Reservation> reservation = findBy(request);

        if (reservation.isPresent()) {
            throw new ReservationException(String.format("%s은 이미 예약되었습니다.", reservation));
        }
        return reservationRepository.save(getReservation(request));
    }

    private Optional<Reservation> findBy(ReservationRequest request) {
        return reservationRepository.findBy(request.getDate(), request.getTime());
    }

    private Reservation getReservation(ReservationRequest request) {
        return new Reservation(request.getDate(), request.getTime(), request.getName());
    }

    public List<ReservationResponse> check(String date) {
        List<Reservation> reservations = reservationRepository.findAllBy(date);

        return reservations.stream()
            .map(this::getResponse)
            .collect(Collectors.toList());
    }

    private ReservationResponse getResponse(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getDate().toString(),
            reservation.getTime().toString(),
            reservation.getName()
        );
    }

    @Transactional
    public void cancel(String date, String time) {
        reservationRepository.delete(date, time);
    }
}
