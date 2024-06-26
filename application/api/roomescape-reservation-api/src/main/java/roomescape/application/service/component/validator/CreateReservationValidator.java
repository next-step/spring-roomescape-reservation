package roomescape.application.service.component.validator;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.CreateReservationValidateException;
import roomescape.domain.reservation.ReservationRepository;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservationtime.ReservationTime;

@Component
public class CreateReservationValidator {

    private final ReservationRepository repository;

    public CreateReservationValidator(ReservationRepository repository) {
        this.repository = repository;
    }

    public void validate(ReservationDate reservationDate, ReservationTime reservationTime) {
        if (repository.existByDateAndTimeId(reservationDate.date(), reservationTime.getId())) {
            throw CreateReservationValidateException.existTime(reservationDate, reservationTime);
        }
    }
}
