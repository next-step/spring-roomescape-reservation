package nextstep.domain;

import java.util.HashMap;
import java.util.Map;

public class Identity {
    private static final Map<Class<?>, Long> identities = new HashMap<>();

    static final Long INITIAL_ID = 1L;

    public static <T> Long getId(Class<T> type) {
        identities.get(type);
        if (identities.containsKey(type)) {
            Long newId = identities.get(type) + 1L;
            identities.put(type, newId);
        } else {
            identities.put(type, INITIAL_ID);
        }
        return identities.get(type);
    }

    public static <T> void reset(Class<T> type) {
        identities.remove(type);
    }
}
