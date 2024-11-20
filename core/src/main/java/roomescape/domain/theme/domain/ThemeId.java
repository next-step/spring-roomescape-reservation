package roomescape.domain.theme.domain;

import java.util.Objects;

public record ThemeId(
        Long value
) {

    public ThemeId {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("value must not be null");
        }
    }
}
