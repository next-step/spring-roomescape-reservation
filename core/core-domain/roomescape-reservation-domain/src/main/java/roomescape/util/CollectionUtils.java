package roomescape.util;

import java.util.Collection;
import java.util.Objects;

public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException(this.getClass().getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static boolean isEmpty(Collection<?> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }
}
