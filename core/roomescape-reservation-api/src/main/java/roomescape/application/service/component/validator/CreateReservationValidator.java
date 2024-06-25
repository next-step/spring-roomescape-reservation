package roomescape.application.service.component.validator;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.CreateReservationValidateException;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.repository.ReservationRepository;

@Component
public class CreateReservationValidator {

    private final ReservationRepository reservationRepository;

    public CreateReservationValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validate(ReservationDate reservationDate, ReservationTime reservationTime) {
        reservationRepository.findByDateAndTimeId(reservationDate.date(), reservationTime.getId())
                .ifPresent(entity -> {
                    throw CreateReservationValidateException.existTime(reservationDate, reservationTime);
                });
    }
}
