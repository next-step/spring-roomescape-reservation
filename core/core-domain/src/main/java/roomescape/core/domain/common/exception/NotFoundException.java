package roomescape.core.domain.common.exception;

public class NotFoundException extends BusinessException {

    private static final int NOT_FOUND_STATUS_CODE = 404;

    protected NotFoundException(final String message) {
        super(NOT_FOUND_STATUS_CODE, message);
    }
}
