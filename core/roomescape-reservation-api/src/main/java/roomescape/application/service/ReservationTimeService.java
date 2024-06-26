package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.service.command.CreateReservationTimeCommand;
import roomescape.application.service.command.DeleteReservationTimeCommand;
import roomescape.application.service.component.validator.CreateReservationTimeValidator;
import roomescape.application.service.component.validator.DeleteReservationTimeValidator;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimeRepository;
import roomescape.domain.reservationtime.ReservationTimes;
import roomescape.domain.reservationtime.vo.ReservationTimeId;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final DeleteReservationTimeValidator deleteReservationTimeValidator;
    private final CreateReservationTimeValidator createReservationTimeValidator;

    public ReservationTimeService(
            ReservationTimeRepository reservationTimeRepository,
            DeleteReservationTimeValidator deleteReservationTimeValidator,
            CreateReservationTimeValidator createReservationTimeValidator
    ) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.deleteReservationTimeValidator = deleteReservationTimeValidator;
        this.createReservationTimeValidator = createReservationTimeValidator;
    }

    public ReservationTime create(CreateReservationTimeCommand command) {
        ReservationTime reservationTime = command.toReservationTime();
        createReservationTimeValidator.validate(reservationTime);

        return reservationTimeRepository.save(reservationTime);
    }

    public ReservationTimes findAll() {
        return reservationTimeRepository.findAll();
    }

    public void delete(DeleteReservationTimeCommand command) {
        ReservationTimeId reservationTimeId = command.toReservationTimeId();

        deleteReservationTimeValidator.validate(reservationTimeId);
        reservationTimeRepository.delete(command.getReservationTimeId());
    }
}
