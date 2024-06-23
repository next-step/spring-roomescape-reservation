package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;
import roomescape.application.error.key.ApplicationErrorKey;
import roomescape.application.error.key.ApplicationErrorKeys;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.theme.vo.ThemeId;

import java.util.List;

import static roomescape.application.error.code.ApplicationErrorCode.NOT_FOUND_ENTITY_RESERVATION_TIME;
import static roomescape.application.error.code.ApplicationErrorCode.NOT_FOUND_ENTITY_THEME;

public class NotFoundEntityException extends NotFoundException {

    private static final String ERROR_KEY_TIME_ID = "timeId";
    private static final String ERROR_KEY_THEME_ID = "themeId";

    public NotFoundEntityException(ApplicationErrorCode code, ApplicationErrorKeys keys) {
        super(code, keys);
    }

    public static NotFoundEntityException reservationTime(ReservationTimeId timeId) {
        return new NotFoundEntityException(
                NOT_FOUND_ENTITY_RESERVATION_TIME,
                new ApplicationErrorKeys(
                        List.of(
                                new ApplicationErrorKey(ERROR_KEY_TIME_ID, timeId.id().toString())
                        )
                )
        );
    }

    public static NotFoundEntityException theme(ThemeId themeId) {
        return new NotFoundEntityException(
                NOT_FOUND_ENTITY_THEME,
                new ApplicationErrorKeys(
                        List.of(
                                new ApplicationErrorKey(ERROR_KEY_THEME_ID, themeId.id().toString())
                        )
                )
        );
    }
}
