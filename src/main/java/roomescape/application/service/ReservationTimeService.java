package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.mapper.ReservationTimeEntityMapper;
import roomescape.application.mapper.ReservationTimeMapper;
import roomescape.application.service.command.CreateReservationTimeCommand;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.entity.ReservationTimeEntity;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime createReservationTime(CreateReservationTimeCommand createReservationTimeCommand) {
        ReservationTime reservationTime = createReservationTimeCommand.toReservationTime();
        ReservationTimeEntity savedReservationTime =
                reservationTimeRepository.save(ReservationTimeEntityMapper.toReservationTimeEntity(reservationTime));

        return ReservationTimeMapper.toReservationTime(savedReservationTime);
    }
}
