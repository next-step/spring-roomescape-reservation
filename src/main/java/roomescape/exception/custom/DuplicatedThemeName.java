package roomescape.exception.custom;

public class DuplicatedThemeName extends RuntimeException {
    public DuplicatedThemeName() {
        super();
    }

    public DuplicatedThemeName(String message) {
        super(message);
    }

    public DuplicatedThemeName(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedThemeName(Throwable cause) {
        super(cause);
    }
}
