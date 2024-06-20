package roomescape.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 400, "잘못된 입력 값입니다."),
  NOT_FOUND(HttpStatus.NOT_FOUND, 404, "데이터를 찾을 수 없습니다.");

  private final HttpStatus httpStatus;
  private final Integer code;
  private final String message;
}
