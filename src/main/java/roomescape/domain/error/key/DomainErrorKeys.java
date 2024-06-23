package roomescape.domain.error.key;

import java.util.List;
import java.util.stream.Collectors;

public class DomainErrorKeys {

    private final List<DomainErrorKey> keys;

    public DomainErrorKeys(List<DomainErrorKey> keys) {
        this.keys = keys;
    }

    public String toMessage() {
        return keys.stream()
                .map(DomainErrorKey::toMessage)
                .collect(Collectors.joining(", "));
    }
}
