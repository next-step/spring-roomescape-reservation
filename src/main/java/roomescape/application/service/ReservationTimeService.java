package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.mapper.ReservationTimeEntityMapper;
import roomescape.application.mapper.ReservationTimeMapper;
import roomescape.application.service.command.CreateReservationTimeCommand;
import roomescape.application.service.command.DeleteReservationTimeCommand;
import roomescape.application.service.component.validator.CreateReservationTimeValidator;
import roomescape.application.service.component.validator.DeleteReservationTimeValidator;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimes;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final DeleteReservationTimeValidator deleteReservationTimeValidator;
    private final CreateReservationTimeValidator createReservationTimeValidator;

    public ReservationTimeService(
            ReservationTimeRepository reservationTimeRepository,
            DeleteReservationTimeValidator deleteReservationTimeValidator,
            CreateReservationTimeValidator createReservationTimeValidator) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.deleteReservationTimeValidator = deleteReservationTimeValidator;
        this.createReservationTimeValidator = createReservationTimeValidator;
    }

    public ReservationTime createReservationTime(CreateReservationTimeCommand command) {
        ReservationTime reservationTime = command.toReservationTime();
        createReservationTimeValidator.validate(reservationTime);

        return ReservationTimeMapper.toReservationTime(
                reservationTimeRepository.save(ReservationTimeEntityMapper.toReservationTimeEntity(reservationTime))
        );
    }

    public ReservationTimes findAllReservationTimes() {
        return ReservationTimeMapper.toReservationTimes(reservationTimeRepository.findAll());
    }

    public void deleteReservationTime(DeleteReservationTimeCommand command) {
        ReservationTimeId reservationTimeId = command.toReservationTimeId();

        deleteReservationTimeValidator.validate(reservationTimeId);
        reservationTimeRepository.delete(command.getReservationTimeId());
    }
}
