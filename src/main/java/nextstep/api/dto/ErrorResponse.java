package nextstep.api.dto;

public class ErrorResponse {
    private String message;

    public ErrorResponse() {
    }

    private ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorResponse of(Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
