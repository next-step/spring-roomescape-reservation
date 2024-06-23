package roomescape.domain.error.exception;

import roomescape.domain.error.code.DomainErrorCode;
import roomescape.domain.error.key.DomainErrorKey;
import roomescape.domain.error.key.DomainErrorKeys;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static roomescape.domain.error.code.DomainErrorCode.CANNOT_CREATE_RESERVATION_FOR_PAST_TIME;

public class CreateReservationValidateException extends DomainException {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    private static final String ERROR_KEY_NAME_RESERVATION_TIME = "예약 시간";

    public CreateReservationValidateException(DomainErrorCode code, DomainErrorKeys keys) {
        super(code, keys);
    }

    public static CreateReservationValidateException pastTime(LocalDateTime dateTime) {
        return new CreateReservationValidateException(
                CANNOT_CREATE_RESERVATION_FOR_PAST_TIME,
                DomainErrorKeys.of(
                        new DomainErrorKey(
                                ERROR_KEY_NAME_RESERVATION_TIME,
                                dateTime.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                )
        );
    }
}
