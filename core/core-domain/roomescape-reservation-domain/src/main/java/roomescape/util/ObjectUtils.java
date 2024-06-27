package roomescape.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ObjectUtils {

    private ObjectUtils() {
        throw new UnsupportedOperationException(this.getClass().getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static boolean hasNull(Object... args) {
        if (Objects.isNull(args)) return true;

        List<Object> objects = toList(args);

        return !CollectionUtils.isEmpty(objects) && objects.stream().anyMatch(Objects::isNull);
    }

    private static List<Object> toList(Object... args) {
        if (Objects.isNull(args)) {
            return Collections.emptyList();
        }

        return Arrays.stream(args).toList();
    }
}
