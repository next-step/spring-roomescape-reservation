package roomescape.domain.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int statusCode;
    private final CustomErrorCode errorCode;

    protected BusinessException(final int statusCode, final CustomErrorCode errorCode, final String message) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
}
