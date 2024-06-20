package roomescape.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.errors.ErrorCode;

@Getter
@AllArgsConstructor
public class SpringRoomException extends RuntimeException{
  private final ErrorCode errorCode;
}
