package roomescape.error;

public class RoomescapeExceptionResponse {
    private String message;

    public RoomescapeExceptionResponse(String message) {
        this.message = message;
    }

    public static RoomescapeExceptionResponse from(RoomescapeErrorMessage roomescapeErrorMessage,
        String exceptionMessage) {
        return new RoomescapeExceptionResponse(exceptionMessage + roomescapeErrorMessage.getMessage());
    }

    public static RoomescapeExceptionResponse of(RoomescapeErrorMessage roomescapeErrorMessage) {
        return new RoomescapeExceptionResponse(roomescapeErrorMessage.getMessage());
    }

    public String getMessage() {
        return message;
    }
}
