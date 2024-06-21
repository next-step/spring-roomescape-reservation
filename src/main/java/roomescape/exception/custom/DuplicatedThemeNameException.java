package roomescape.exception.custom;

public class DuplicatedThemeNameException extends RuntimeException {
    public DuplicatedThemeNameException() {
        super();
    }

    public DuplicatedThemeNameException(String message) {
        super(message);
    }

    public DuplicatedThemeNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedThemeNameException(Throwable cause) {
        super(cause);
    }
}
