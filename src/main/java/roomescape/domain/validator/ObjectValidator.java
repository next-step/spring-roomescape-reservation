package roomescape.domain.validator;

import roomescape.util.ObjectUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ObjectValidator {

    private static final String NOT_NULL_MESSAGE = "%s는 null값이면 안됩니다.";

    public static void validateNotNull(Object... objects) {
        String classNames = Stream.of(objects)
                .map(Object::getClass)
                .map(Class::getName)
                .collect(Collectors.joining(", "));

        if (ObjectUtils.hasNull(objects)) {
            throw new IllegalArgumentException(String.format(NOT_NULL_MESSAGE, classNames));
        }
    }
}
