package roomescape.domain.common.exception;

public class NotFoundException extends BusinessException {

    private static final int NOT_FOUND_STATUS_CODE = 404;

    protected NotFoundException(final CustomErrorCode errorCode, final String message) {
        super(NOT_FOUND_STATUS_CODE, errorCode, message);
    }
}
