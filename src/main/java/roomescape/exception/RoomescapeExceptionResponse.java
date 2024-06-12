package roomescape.exception;

public class RoomescapeExceptionResponse {

    private String code;

    private String message;

    private RoomescapeExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static RoomescapeExceptionResponse from(RoomescapeErrorCode roomescapeErrorCode,
        String exceptionMessage) {
        return new RoomescapeExceptionResponse(roomescapeErrorCode.name(),
            exceptionMessage + roomescapeErrorCode.getMessage());
    }

    public static RoomescapeExceptionResponse of(RoomescapeErrorCode roomescapeErrorCode) {
        return new RoomescapeExceptionResponse(roomescapeErrorCode.name(),
            roomescapeErrorCode.getMessage());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
