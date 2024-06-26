package roomescape.error.exception;

import roomescape.error.code.DomainErrorCode;
import roomescape.error.key.DomainErrorKey;
import roomescape.error.key.DomainErrorKeys;

import static roomescape.error.code.DomainErrorCode.*;

public class NotFoundDomainException extends DomainException {

    private static final String RESERVATION_ID = "reservationId";
    private static final String RESERVATION_TIME_ID = "timeId";
    private static final String THEME_ID = "themeId";

    public NotFoundDomainException(DomainErrorCode code, DomainErrorKeys keys) {
        super(code, keys);
    }

    public static NotFoundDomainException notFoundReservation(Long reservationId) {
        return new NotFoundDomainException(
                NOT_FOUND_RESERVATION,
                DomainErrorKeys.of(
                        new DomainErrorKey(RESERVATION_ID, reservationId.toString())
                )
        );
    }

    public static NotFoundDomainException notFoundReservationTime(Long timeId) {
        return new NotFoundDomainException(
                NOT_FOUND_RESERVATION_TIME,
                DomainErrorKeys.of(
                        new DomainErrorKey(RESERVATION_TIME_ID, timeId.toString())
                )
        );
    }

    public static NotFoundDomainException notFoundTheme(Long themeId) {
        return new NotFoundDomainException(
                NOT_FOUND_THEME,
                DomainErrorKeys.of(
                        new DomainErrorKey(THEME_ID, themeId.toString())
                )
        );
    }
}
