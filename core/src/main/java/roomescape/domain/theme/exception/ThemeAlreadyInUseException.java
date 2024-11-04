package roomescape.domain.theme.exception;

import roomescape.domain.common.exception.BadRequestException;
import roomescape.domain.common.exception.CustomErrorCode;
import roomescape.domain.theme.domain.ThemeId;

public class ThemeAlreadyInUseException extends BadRequestException {

    public ThemeAlreadyInUseException(final String message) {
        super(CustomErrorCode.TH406, message);
    }

    public static ThemeAlreadyInUseException from(final ThemeId themeId) {
        return new ThemeAlreadyInUseException("theme(themeId=%s) is already in use".formatted(themeId.value()));
    }
}
