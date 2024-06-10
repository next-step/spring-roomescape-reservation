package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class RoomescapeExceptionHandler {
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RoomescapeExceptionResponse IllegalArgumentException(IllegalArgumentException e) {
		return RoomescapeExceptionResponse.from(RoomescapeErrorCode.ILLEGAL_INPUT_VALUE_EXCEPTION, e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RoomescapeExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return RoomescapeExceptionResponse.from(RoomescapeErrorCode.ILLEGAL_INPUT_VALUE_EXCEPTION,e.getBindingResult().getFieldError().getDefaultMessage());
	}

	@ExceptionHandler(DateTimeParseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RoomescapeExceptionResponse hanleParseException() {
		return RoomescapeExceptionResponse.of(RoomescapeErrorCode.ILLEGAL_DATETIME_FORMAT_EXCEPTION);
	}

	@ExceptionHandler(PastDateTimeExeption.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RoomescapeExceptionResponse handlePastDateTimeException(PastDateTimeExeption e) {
		return RoomescapeExceptionResponse.of(e.getErrorCode());
	}
}
