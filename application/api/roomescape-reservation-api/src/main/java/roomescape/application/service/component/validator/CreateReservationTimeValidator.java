package roomescape.application.service.component.validator;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.CreateReservationTimeValidateException;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimeRepository;

@Component
public class CreateReservationTimeValidator {

    private final ReservationTimeRepository repository;

    public CreateReservationTimeValidator(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public void validate(ReservationTime reservationTime) {
        if (repository.existByStartAt(reservationTime.getStartAt())) {
            throw CreateReservationTimeValidateException.existTime(reservationTime);
        }
    }
}
