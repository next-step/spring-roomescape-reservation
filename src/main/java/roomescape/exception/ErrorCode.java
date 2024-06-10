package roomescape.exception;

public enum ErrorCode {

	// @formatter:off
	INVALID_PARAMETER("RES-0001", "유효하지 않은 값입니다."),
	INVALID_TIME("RES-0001", "유효하지 않은 시간 값입니다."),
	INVALID_RESERVATION("RES-0002", "유효하지 않은 예약 정보입니다."),

	DUPLICATE_RESERVATION("RES-0003", "중복 예약은 불가능합니다."),
	PAST_RESERVATION("RES-0004", "지나간 날짜와 시간에 대한 예약 생성은 불가능합니다."),

	NOT_FOUND_RESERVATION("RES-0005", "해당 예약이 없습니다."),
	NOT_FOUND_RESERVATION_TIME("RES-0006", "해당 예약 시간이 없습니다."),
	NOT_FOUND_THEME("RES-0007", "해당 테마가 없습니다.");
	// @formatter:on

	private final String code;

	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

}
