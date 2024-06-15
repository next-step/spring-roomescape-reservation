package roomescape.application.service.exception;

import org.springframework.http.HttpStatusCode;
import roomescape.application.error.code.ErrorCode;

public class ServiceException extends RuntimeException {

    private final ErrorCode code;

    public ServiceException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }

    public HttpStatusCode getHttpStatus() {
        return this.code.getHttpStatus();
    }
}
