package roomescape.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 400),
  RESERVATION_TIME_CANNOT_BE_DELETED(HttpStatus.BAD_REQUEST, 400),
  NOT_FOUND(HttpStatus.NOT_FOUND, 404);


  private final HttpStatus httpStatus;
  private final int code;

  ErrorCode(HttpStatus httpStatus, int code) {
    this.httpStatus = httpStatus;
    this.code = code;
  }
}
