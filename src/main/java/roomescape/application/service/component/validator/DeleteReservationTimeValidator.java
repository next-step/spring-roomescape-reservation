package roomescape.application.service.component.validator;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.DeleteReservationTimeValidateException;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.repository.ReservationRepository;

import static roomescape.application.error.code.ApplicationErrorCode.CANNOT_DELETE_EXIST_RESERVATION_AT_THIS_TIME;

@Component
public class DeleteReservationTimeValidator {

    private final ReservationRepository reservationRepository;

    public DeleteReservationTimeValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validate(ReservationTimeId timeId) {
        reservationRepository.findByTimeId(timeId.id())
                .ifPresent(entity -> {
                    throw new DeleteReservationTimeValidateException(CANNOT_DELETE_EXIST_RESERVATION_AT_THIS_TIME);
                });
    }
}
