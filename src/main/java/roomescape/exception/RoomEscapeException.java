package roomescape.exception;

public class RoomEscapeException extends RuntimeException {

	private final ErrorCode errorCode;

	public RoomEscapeException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}

}
