package roomescape.application.error.key;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationErrorKeys {

    private final List<ApplicationErrorKey> keys;

    public ApplicationErrorKeys(List<ApplicationErrorKey> keys) {
        this.keys = keys;
    }

    public String toMessage() {
        return keys.stream()
                .map(ApplicationErrorKey::toMessage)
                .collect(Collectors.joining(", "));
    }
}
