package nextstep.application;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.domain.Reservation;
import nextstep.domain.repository.ReservationRepository;
import nextstep.presentation.dto.ReservationRequest;
import nextstep.presentation.dto.ReservationResponse;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Integer make(ReservationRequest request) {
        Reservation reservation = getReservation(request);
        return reservationRepository.save(reservation);
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

    public void cancel(String date, String time) {
        reservationRepository.delete(date, time);
    }
}
