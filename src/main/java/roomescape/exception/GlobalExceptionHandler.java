package roomescape.exception;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(RoomEscapeException.class)
	public ProblemDetail handleRoomEscapeExceptions(RoomEscapeException ex, WebRequest request) {
		ErrorCode errorCode = ex.getErrorCode();
		HttpStatus status = getStatusForErrorCode(errorCode);

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, errorCode.getMessage());
		problemDetail.setTitle("API Error");
		problemDetail.setInstance(URI.create(request.getDescription(false)));
		problemDetail.setProperty("errorCode", errorCode.getCode());

		logger.error("Error occurred: {}", problemDetail, ex);

		return problemDetail;
	}

	private HttpStatus getStatusForErrorCode(ErrorCode errorCode) {
		return switch (errorCode) {
			case INVALID_TIME, INVALID_RESERVATION, PAST_RESERVATION -> HttpStatus.BAD_REQUEST;
			case DUPLICATE_RESERVATION -> HttpStatus.CONFLICT;
			case NOT_FOUND_RESERVATION, NOT_FOUND_RESERVATION_TIME, NOT_FOUND_THEME -> HttpStatus.NOT_FOUND;
		};
	}

}
