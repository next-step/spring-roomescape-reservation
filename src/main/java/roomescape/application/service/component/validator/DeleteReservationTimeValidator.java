package roomescape.application.service.component.validator;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.DeleteReservationTimeValidateException;
import roomescape.repository.ReservationRepository;

import static roomescape.application.error.code.ApplicationErrorCode.EXIST_RESERVATION_AT_THIS_TIME;

@Component
public class DeleteReservationTimeValidator {

    private final ReservationRepository reservationRepository;

    public DeleteReservationTimeValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validate(Long reservationTimeId) {
        reservationRepository.findByTimeId(reservationTimeId)
                .ifPresent(entity -> {
                    throw new DeleteReservationTimeValidateException(EXIST_RESERVATION_AT_THIS_TIME);
                });
    }
}
