package roomescape.util;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class ObjectUtils {

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
