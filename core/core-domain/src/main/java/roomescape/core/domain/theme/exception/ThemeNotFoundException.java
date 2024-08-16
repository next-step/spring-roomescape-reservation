package roomescape.core.domain.theme.exception;

import roomescape.core.domain.theme.ThemeId;

public class ThemeNotFoundException extends ThemeException {

    public ThemeNotFoundException(final String message) {
        super(message);
    }

    public static ThemeNotFoundException from(final ThemeId themeId) {
        return new ThemeNotFoundException("Cannot find theme for themeId=%d".formatted(themeId.value()));
    }
}
