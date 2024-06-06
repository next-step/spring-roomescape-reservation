package roomescape.domain.validator;

public abstract class IdValidator {

    public static void validate(Long id) {
        if (id < 1) {
            throw new IllegalArgumentException("id값은 1이상이어야 합니다.");
        }
    }
}
