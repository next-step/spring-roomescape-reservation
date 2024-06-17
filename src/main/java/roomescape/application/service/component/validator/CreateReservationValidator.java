package roomescape.application.service.component.validator;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.CreateReservationValidateException;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.repository.ReservationRepository;

import static roomescape.application.error.code.ApplicationErrorCode.CANNOT_CREATE_EXIST_RESERVATION_AT_THIS_TIME;

@Component
public class CreateReservationValidator {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final ReservationRepository reservationRepository;

    public CreateReservationValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validate(ReservationDate date, ReservationTime time) {
        reservationRepository.findByDateAndTimeId(date.date(), time.getId())
                .ifPresent(entity -> {
                    throw new CreateReservationValidateException(
                            CANNOT_CREATE_EXIST_RESERVATION_AT_THIS_TIME,
                            String.format("[date:%s, timeId:%d] 이 시간에 존재하는 예약이 있어 예약을 생성할 수 없습니다.",
                                    date.getFormatted(DATE_FORMAT),
                                    time.getId()
                            )
                    );
                });
    }
}
