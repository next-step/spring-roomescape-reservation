package roomescape.core.domain.theme.exception;

import roomescape.core.domain.common.exception.BadRequestException;
import roomescape.core.domain.common.exception.CustomErrorCode;
import roomescape.core.domain.theme.ThemeId;

public class ThemeAlreadyInUseException extends BadRequestException {

    public ThemeAlreadyInUseException(final String message) {
        super(CustomErrorCode.TH406, message);
    }

    public static ThemeAlreadyInUseException from(final ThemeId themeId) {
        return new ThemeAlreadyInUseException("theme(themeId=%s) is already in use".formatted(themeId.value()));
    }
}
