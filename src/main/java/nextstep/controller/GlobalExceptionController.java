package nextstep.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import nextstep.domain.reservation.exception.ReservationIllegalArgumentException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionController {

  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
    return processFieldErrors(fieldErrors);
  }

  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(ReservationIllegalArgumentException.class)
  public ErrorResponse domain400Exception(ReservationIllegalArgumentException ex) {
    return ErrorResponse.builder()
        .status(400)
        .message("domain error")
        .detail(ex.getMessage())
        .build();
  }

  private ErrorResponse processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
    ErrorResponse error = ErrorResponse.builder()
        .status(BAD_REQUEST.value())
        .message("validation error")
        .build();
    for (org.springframework.validation.FieldError fieldError: fieldErrors) {
      error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return error;
  }

  @Builder(toBuilder = true)
  @Getter
  static class ErrorResponse {
    private final int status;
    private final String message;
    private String detail;

    public void addFieldError(String field, String message) {
      StringBuffer sb = new StringBuffer("필드 목록 : \n");
      sb.append("필드 : %s, 내용 : %s \n".formatted(field, message));
      this.detail = sb.toString();
    }
  }
}
