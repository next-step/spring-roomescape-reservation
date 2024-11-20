package roomescape.global.rest.error;

import lombok.Getter;
import roomescape.domain.common.exception.BusinessException;
import roomescape.domain.common.exception.CustomErrorCode;

@Getter
public class ErrorDetails {

    private final CustomErrorCode errorCode;
    private final String errorCodeDescription;
    private final String errorMessage;

    private ErrorDetails(final CustomErrorCode errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorCodeDescription = errorCode.getDescription();
        this.errorMessage = errorMessage;
    }

    public static ErrorDetails from(final BusinessException e) {
        return new ErrorDetails(e.getErrorCode(), e.getMessage());
    }

    public static ErrorDetails from(final IllegalArgumentException e) {
        return new ErrorDetails(CustomErrorCode.NOT_DEFINED, e.getMessage());
    }

    public static ErrorDetails serverError() {
        return new ErrorDetails(CustomErrorCode.SERVER_ERROR, CustomErrorCode.SERVER_ERROR.getDescription());
    }
}
