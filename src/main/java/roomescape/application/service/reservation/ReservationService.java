package roomescape.application.service.reservation;

import org.springframework.stereotype.Service;
import roomescape.application.dto.command.CreateReservationCommand;
import roomescape.application.dto.result.CreateReservationResult;
import roomescape.application.dto.result.GetReservationResult;
import roomescape.domain.Reservation;
import roomescape.repository.reservation.ReservationRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<GetReservationResult> getReservations() {
        List<Reservation> reservationList = reservationRepository.findAllReservations();
        if(reservationList.isEmpty()) {
            return Collections.emptyList();
        }

        return reservationList
                .stream()
                .map(reservation -> GetReservationResult.builder()
                        .id(reservation.getId())
                        .name(reservation.getName())
                        .date(reservation.getDate())
                        .time(reservation.getTime())
                        .build()).collect(Collectors.toList());
    }

    public CreateReservationResult createReservation(CreateReservationCommand command) {
        return CreateReservationResult.of(reservationRepository.createReservation(command));
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteReservation(id);
    }
}
