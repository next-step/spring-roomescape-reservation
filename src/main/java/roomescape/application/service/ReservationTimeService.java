package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.mapper.ReservationTimeEntityMapper;
import roomescape.application.mapper.ReservationTimeMapper;
import roomescape.application.service.command.CreateReservationTimeCommand;
import roomescape.application.service.command.DeleteReservationTimeCommand;
import roomescape.application.service.component.validator.DeleteReservationTimeValidator;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimes;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.entity.ReservationTimeEntity;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final DeleteReservationTimeValidator deleteReservationTimeValidator;

    public ReservationTimeService(
            ReservationTimeRepository reservationTimeRepository,
            DeleteReservationTimeValidator deleteReservationTimeValidator
    ) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.deleteReservationTimeValidator = deleteReservationTimeValidator;
    }

    public ReservationTime createReservationTime(CreateReservationTimeCommand command) {
        ReservationTime reservationTime = command.toReservationTime();
        ReservationTimeEntity savedReservationTimeEntity =
                reservationTimeRepository.save(ReservationTimeEntityMapper.toReservationTimeEntity(reservationTime));

        return ReservationTimeMapper.toReservationTime(savedReservationTimeEntity);
    }

    public ReservationTimes findAllReservationTimes() {
        return ReservationTimeMapper.toReservationTimes(reservationTimeRepository.findAll());
    }

    public void deleteReservationTime(DeleteReservationTimeCommand command) {
        deleteReservationTimeValidator.validate(command.getReservationTimeId());
        reservationTimeRepository.delete(command.getReservationTimeId());
    }
}
