package nextstep.schedules.exception;

public class ThemesNotFoundException extends IllegalArgumentException {
    private static final String MESSAGE = "존재하지 않는 테마입니다.";

    public ThemesNotFoundException() {
        super(MESSAGE);
    }
}
