package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.mapper.ReservationEntityMapper;
import roomescape.application.mapper.ReservationMapper;
import roomescape.application.service.command.CreateReservationCommand;
import roomescape.application.service.command.DeleteReservationCommand;
import roomescape.application.service.component.reader.ReservationTimeReader;
import roomescape.application.service.component.validator.CreateReservationValidator;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeReader reservationTimeReader;
    private final CreateReservationValidator createReservationValidator;

    public ReservationCommandService(
            ReservationRepository reservationRepository,
            ReservationTimeReader reservationTimeReader,
            CreateReservationValidator createReservationValidator
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeReader = reservationTimeReader;
        this.createReservationValidator = createReservationValidator;
    }

    public Reservation createReservation(CreateReservationCommand command) {
        ReservationTimeId reservationTimeId = new ReservationTimeId(command.getReservationTimeId());
        ReservationDate reservationDate = new ReservationDate(command.getReservationDate());
        ReservationTime reservationTime = reservationTimeReader.readById(reservationTimeId);

        createReservationValidator.validate(reservationDate, reservationTime);

        Reservation createdReservation = Reservation.create(
                ReservationId.empty(),
                new ReservationName(command.getReservationName()),
                reservationDate,
                reservationTime
        );

        return ReservationMapper.toReservation(
                reservationRepository.save(ReservationEntityMapper.toReservationEntity(createdReservation)),
                reservationTime
        );
    }

    public void deleteReservation(DeleteReservationCommand command) {
        reservationRepository.delete(command.getReservationId());
    }
}
