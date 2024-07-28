package roomescape.core.domain.theme.exception;

import roomescape.core.domain.common.exception.DomainException;

public class ThemeException extends DomainException {

    public ThemeException(final String message) {
        super(message);
    }

    public static ThemeException nullField(final String nullFieldName) {
        return new ThemeException("Field %s must not be null".formatted(nullFieldName));
    }
}
