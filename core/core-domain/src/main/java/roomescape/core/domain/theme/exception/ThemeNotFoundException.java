package roomescape.core.domain.theme.exception;

import roomescape.core.domain.common.exception.CustomErrorCode;
import roomescape.core.domain.common.exception.NotFoundException;
import roomescape.core.domain.theme.ThemeId;

public class ThemeNotFoundException extends NotFoundException {

    public ThemeNotFoundException(final String message) {
        super(CustomErrorCode.TH404, message);
    }

    public static ThemeNotFoundException from(final ThemeId themeId) {
        return new ThemeNotFoundException("Cannot find theme for themeId=%d".formatted(themeId.value()));
    }
}
