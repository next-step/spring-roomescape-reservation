package roomescape.core.domain.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int statsCode;

    protected BusinessException(final int statsCode, final String message) {
        super(message);
        this.statsCode = statsCode;
    }
}
