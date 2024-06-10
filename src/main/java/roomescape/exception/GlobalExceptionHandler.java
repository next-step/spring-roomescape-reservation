package roomescape.exception;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static roomescape.exception.ErrorCode.INVALID_PARAMETER;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, INVALID_PARAMETER.getMessage());
		return handleExceptionInternal(ex, problemDetail, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(RoomEscapeException.class)
	public ResponseEntity<ProblemDetail> handleRoomEscapeExceptions(RoomEscapeException ex, WebRequest request) {
		ErrorCode errorCode = ex.getErrorCode();
		HttpStatus status = getStatusForErrorCode(errorCode);

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, errorCode.getMessage());
		problemDetail.setTitle("API Error");
		problemDetail.setInstance(URI.create(request.getDescription(false)));
		problemDetail.setProperty("errorCode", errorCode.getCode());

		logger.error("Error occurred: {}", problemDetail, ex);

		return ResponseEntity.status(status).body(problemDetail);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ProblemDetail> handleAllExceptions(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "An unexpected error occurred.");
		problemDetail.setTitle("Internal Server Error");
		problemDetail.setInstance(URI.create(request.getDescription(false)));

		logger.error("Unexpected error occurred: {}", problemDetail, ex);

		return ResponseEntity.status(status).body(problemDetail);
	}

	private HttpStatus getStatusForErrorCode(ErrorCode errorCode) {
		return switch (errorCode) {
			case INVALID_PARAMETER, INVALID_THEME_NAME, INVALID_THEME_DESCRIPTION, INVALID_THEME_THUMBNAIL,
					INVALID_TIME, INVALID_RESERVATION, PAST_RESERVATION ->
				HttpStatus.BAD_REQUEST;
			case DUPLICATE_RESERVATION -> HttpStatus.CONFLICT;
			case NOT_FOUND_RESERVATION, NOT_FOUND_RESERVATION_TIME, NOT_FOUND_THEME -> HttpStatus.NOT_FOUND;
		};
	}

}
