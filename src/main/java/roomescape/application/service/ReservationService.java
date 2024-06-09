package roomescape.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.application.mapper.ReservationEntityMapper;
import roomescape.application.mapper.ReservationMapper;
import roomescape.application.service.command.CreateReservationCommand;
import roomescape.application.service.command.DeleteReservationCommand;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.Reservations;
import roomescape.repository.ReservationRepository;
import roomescape.repository.entity.ReservationEntity;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservations findAllReservations() {
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();

        return ReservationMapper.toReservations(reservationEntities);
    }

    public Reservation createReservation(CreateReservationCommand createReservationCommand) {
        Reservation reservation = createReservationCommand.toReservation();
        ReservationEntity savedEntity = reservationRepository.save(ReservationEntityMapper.toReservationEntity(reservation));

        return ReservationMapper.toReservation(savedEntity);
    }

    public void deleteReservation(DeleteReservationCommand deleteReservationCommand) {
        reservationRepository.delete(deleteReservationCommand.getReservationId());
    }
}
