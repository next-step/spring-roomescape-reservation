package roomescape.application.service.component.validator;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.CreateReservationTimeValidateException;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import static roomescape.application.error.code.ApplicationErrorCode.CANNOT_CREATE_EXIST_RESERVATION_TIME;

@Component
public class CreateReservationTimeValidator {

    private static final String TIME_FORMAT = "HH:mm";

    private final ReservationTimeRepository reservationTimeRepository;

    public CreateReservationTimeValidator(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void validate(ReservationTime reservationTime) {
        reservationTimeRepository.findByStartAt(reservationTime.getStartAt())
                .ifPresent(entity -> {
                    throw new CreateReservationTimeValidateException(
                            CANNOT_CREATE_EXIST_RESERVATION_TIME,
                            String.format(
                                    "[startAt:%s] 이 시간의 예약 시간은 이미 존재하여 생성할 수 없습니다.",
                                    reservationTime.getFormattedStartAt(TIME_FORMAT)
                            )
                    );
                });
    }
}
