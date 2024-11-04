package roomescape.domain.theme.exception;

import roomescape.domain.common.exception.CustomErrorCode;
import roomescape.domain.common.exception.NotFoundException;
import roomescape.domain.theme.domain.ThemeId;

public class ThemeNotFoundException extends NotFoundException {

    public ThemeNotFoundException(final String message) {
        super(CustomErrorCode.TH404, message);
    }

    public static ThemeNotFoundException from(final ThemeId themeId) {
        return new ThemeNotFoundException("Cannot find theme for themeId=%d".formatted(themeId.value()));
    }
}
