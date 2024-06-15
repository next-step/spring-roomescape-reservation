package roomescape.error;

public class RoomescapeExceptionResponse {

    private String message;

    public RoomescapeExceptionResponse(String message) {
        this.message = message;
    }

    public static RoomescapeExceptionResponse from(String exceptionMessage,
        String roomescapeErrorMessage) {
        return new RoomescapeExceptionResponse(exceptionMessage + roomescapeErrorMessage);
    }

    public static RoomescapeExceptionResponse of(String roomescapeErrorMessage) {
        return new RoomescapeExceptionResponse(roomescapeErrorMessage);
    }

    public String getMessage() {
        return message;
    }
}
