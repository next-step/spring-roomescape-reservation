package roomescape.exception;

public class AlreadyExistsException extends RuntimeException{
	private RoomescapeErrorCode errorCode;

	public AlreadyExistsException(String message) {
		super(message);
		this.errorCode = RoomescapeErrorCode.EXISTS_EXCEPTION;
	}

	public AlreadyExistsException() {
		this("");
	}

	public RoomescapeErrorCode getErrorCode() {
		return errorCode;
	}
}
