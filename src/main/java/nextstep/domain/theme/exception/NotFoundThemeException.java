package nextstep.domain.theme.exception;

public class NotFoundThemeException extends RuntimeException {
    public NotFoundThemeException() {
        super("테마 정보를 찾을 수 없습니다.");
    }
}
