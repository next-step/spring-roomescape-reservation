package roomescape.exception;

public class PastDateTimeExeption extends RuntimeException{
	private RoomescapeErrorCode errorCode;

	public PastDateTimeExeption(String message) {
		super(message);
		this.errorCode = RoomescapeErrorCode.PAST_DATETIME_EXCEPTION;
	}

	public PastDateTimeExeption() {
		this("");
	}

	public RoomescapeErrorCode getErrorCode() {
		return errorCode;
	}
}
