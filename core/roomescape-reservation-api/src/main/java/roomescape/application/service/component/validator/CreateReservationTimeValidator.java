package roomescape.application.service.component.validator;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.CreateReservationTimeValidateException;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Component
public class CreateReservationTimeValidator {


    private final ReservationTimeRepository reservationTimeRepository;

    public CreateReservationTimeValidator(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void validate(ReservationTime reservationTime) {
        reservationTimeRepository.findByStartAt(reservationTime.getStartAt())
                .ifPresent(entity -> {
                    throw CreateReservationTimeValidateException.existTime(reservationTime);
                });
    }
}
