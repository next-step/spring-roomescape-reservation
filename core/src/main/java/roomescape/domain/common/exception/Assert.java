package roomescape.domain.common.exception;

public class Assert {

    private Assert() {
    }

    public static void notNullField(final Object object, final String nullableFieldName) {
        if (object == null) {
            throw new IllegalArgumentException(nullableFieldName + " must not be null");
        }
    }
}
