package roomescape.core.domain.common.exception;

public class BadRequestException extends BusinessException {

    private static final int BAD_REQUEST_STATUS_CODE = 400;

    protected BadRequestException(final String message) {
        super(BAD_REQUEST_STATUS_CODE, message);
    }
}
