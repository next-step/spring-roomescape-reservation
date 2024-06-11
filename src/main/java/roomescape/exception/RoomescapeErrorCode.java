package roomescape.exception;

public enum RoomescapeErrorCode {
	ILLEGAL_INPUT_VALUE_EXCEPTION (" 값을 확인해주세요."),
	ILLEGAL_DATETIME_FORMAT_EXCEPTION ("날짜 및 시간 입력 형식을 확인해주세요."),
	PAST_DATETIME_EXCEPTION("과거 날짜 및 시간을 예약할 수 없습니다."),
	EXISTS_EXCEPTION(" 이미 존재합니다."),
	NOT_EXISTS_EXCEPTION(" 존재하지 않습니다.");

	private String message;

	RoomescapeErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
