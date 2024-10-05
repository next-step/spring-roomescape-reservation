package roomescape.core.domain.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int statsCode;
    private final CustomErrorCode errorCode;

    protected BusinessException(final int statsCode, final CustomErrorCode errorCode, final String message) {
        super(message);
        this.statsCode = statsCode;
        this.errorCode = errorCode;
    }
}
