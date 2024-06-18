package roomescape.application.service.reservation;

import org.springframework.stereotype.Service;
import roomescape.application.dto.command.CreateReservationCommand;
import roomescape.application.dto.command.CreateReservationTimeCommand;
import roomescape.application.dto.result.CreateReservationResult;
import roomescape.application.dto.result.GetReservationResult;
import roomescape.application.dto.result.GetReservationTimeResult;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.reservation.ReservationRepository;
import roomescape.repository.reservationTime.ReservationTimeRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<GetReservationResult> getReservations() {
        List<Reservation> reservationList = reservationRepository.findAllReservations();
        if (reservationList.isEmpty()) {
            return Collections.emptyList();
        }

        return reservationList
                .stream()
                .map(reservation -> GetReservationResult.builder()
                        .id(reservation.getId())
                        .name(reservation.getName())
                        .date(reservation.getDate())
                        .time(
                                GetReservationTimeResult.builder()
                                        .id(reservation.getTime().getId())
                                        .startAt(reservation.getTime().getStartAt())
                                        .build()
                        )
                        .build()).collect(Collectors.toList());
    }

    public CreateReservationResult createReservation(CreateReservationCommand command) {

        ReservationTime reservationTime = reservationTimeRepository.findByTimeId(command.getTimeId());

        return CreateReservationResult.of(
                reservationRepository.createReservation(Reservation.create(command), reservationTime)
        );
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteReservation(id);
    }
}
