package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.service.command.CreateReservationCommand;
import roomescape.application.service.command.DeleteReservationCommand;
import roomescape.application.service.component.creator.ReservationCreator;
import roomescape.application.service.component.reader.ReservationTimeReader;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeReader reservationTimeReader;
    private final ReservationCreator reservationCreator;

    public ReservationCommandService(
            ReservationRepository reservationRepository,
            ReservationTimeReader reservationTimeReader,
            ReservationCreator reservationCreator
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeReader = reservationTimeReader;
        this.reservationCreator = reservationCreator;
    }

    public Reservation createReservation(CreateReservationCommand command) {
        ReservationTimeId reservationTimeId = new ReservationTimeId(command.getReservationTimeId());
        ReservationTime reservationTime = reservationTimeReader.readById(reservationTimeId);

        return reservationCreator.create(
                new ReservationName(command.getReservationName()),
                new ReservationDate(command.getReservationDate()),
                reservationTime
        );
    }

    public void deleteReservation(DeleteReservationCommand command) {
        reservationRepository.delete(command.getReservationId());
    }
}
