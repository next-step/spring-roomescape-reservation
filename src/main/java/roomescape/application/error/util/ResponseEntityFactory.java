package roomescape.application.error.util;

import org.springframework.http.ResponseEntity;
import roomescape.application.error.code.ErrorCode;
import roomescape.application.error.dto.ErrorResponse;

public final class ResponseEntityFactory {

    private ResponseEntityFactory() {
        throw new UnsupportedOperationException(ResponseEntityFactory.class.getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static ResponseEntity<ErrorResponse> create(Exception ex, ErrorCode code) {
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(new ErrorResponse(code, ex.getMessage()));
    }
}
