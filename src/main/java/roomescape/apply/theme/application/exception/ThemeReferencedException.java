package roomescape.apply.theme.application.exception;

public class ThemeReferencedException extends IllegalArgumentException {

    public static final String DEFAULT_MESSAGE = "테마가 다른 예약에서 참조되고 있어 삭제할 수 없습니다.";

    public ThemeReferencedException() {
        super(DEFAULT_MESSAGE);
    }
}
