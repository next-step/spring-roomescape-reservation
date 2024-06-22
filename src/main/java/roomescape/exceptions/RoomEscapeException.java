package roomescape.exceptions;

import lombok.Getter;
import roomescape.errors.ErrorCode;

@Getter
public class RoomEscapeException extends RuntimeException{
  private final ErrorCode errorCode;

  public RoomEscapeException(ErrorCode errorCode, String message){
    super(message);
    this.errorCode = errorCode;
  }
}
