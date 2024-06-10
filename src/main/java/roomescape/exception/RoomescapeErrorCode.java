package roomescape.exception;

public enum RoomescapeErrorCode {
	ILLEGAL_INPUT_VALUE_EXCEPTION ("INPUT-E-1", " 값을 확인해주세요."),
	ILLEGAL_DATETIME_FORMAT_EXCEPTION ("DATETIME-E-2", "날짜 및 시간 입력 형식을 확인해주세요.");

	private String code;
	private String message;

	RoomescapeErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
