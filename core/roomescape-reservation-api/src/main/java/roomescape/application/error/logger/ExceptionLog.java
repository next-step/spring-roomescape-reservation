package roomescape.application.error.logger;

import java.util.Arrays;

public class ExceptionLog {

    private final String message;

    private final String stackTrace;

    private ExceptionLog(String message, String stackTrace) {
        this.message = message;
        this.stackTrace = stackTrace;
    }

    public static ExceptionLog from(Exception ex) {
        return new ExceptionLog(ex.getMessage(), Arrays.toString(ex.getStackTrace()));
    }

    @Override
    public String toString() {
        return "message='" + message + '\n' +
                "stackTrace='" + stackTrace + '\n';
    }
}
