package roomescape.exceptions;

import lombok.Getter;
import roomescape.errors.ErrorCode;

@Getter
public class SpringRoomException extends RuntimeException{
  private final ErrorCode errorCode;

  public SpringRoomException(ErrorCode errorCode, String message){
    super(message);
    this.errorCode = errorCode;
  }
}
