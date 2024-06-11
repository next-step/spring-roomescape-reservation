package roomescape.exception;

public class NotExistsException extends RuntimeException{
	private RoomescapeErrorCode errorCode;

	public NotExistsException(String message) {
		super(message);
		this.errorCode = RoomescapeErrorCode.NOT_EXISTS_EXCEPTION;
	}

	public NotExistsException() {
		this("");
	}

	public RoomescapeErrorCode getErrorCode() {
		return errorCode;
	}
}
