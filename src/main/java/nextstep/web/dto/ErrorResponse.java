package nextstep.web.dto;

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

    public static ErrorResponse from(Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
