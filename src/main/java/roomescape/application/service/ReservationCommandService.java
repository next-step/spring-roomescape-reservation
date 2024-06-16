package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.mapper.ReservationEntityMapper;
import roomescape.application.mapper.ReservationMapper;
import roomescape.application.service.command.CreateReservationCommand;
import roomescape.application.service.command.DeleteReservationCommand;
import roomescape.application.service.component.reader.ReservationTimeReader;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeReader reservationTimeReader;

    public ReservationCommandService(
            ReservationRepository reservationRepository,
            ReservationTimeReader reservationTimeReader
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeReader = reservationTimeReader;
    }

    public Reservation createReservation(CreateReservationCommand command) {
        ReservationTime reservationTime = reservationTimeReader.readById(command.getReservationTimeId());

        Reservation createdReservation = Reservation.create(
                ReservationId.empty(),
                new ReservationName(command.getReservationName()),
                new ReservationDate(command.getReservationDate()),
                reservationTime
        );

        return ReservationMapper.toReservation(
                reservationRepository.save(ReservationEntityMapper.toReservationEntity(createdReservation))
        );
    }

    public void deleteReservation(DeleteReservationCommand command) {
        reservationRepository.delete(command.getReservationId());
    }
}
