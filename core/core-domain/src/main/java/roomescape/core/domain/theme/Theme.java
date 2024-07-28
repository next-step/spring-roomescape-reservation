package roomescape.core.domain.theme;

import lombok.Builder;
import lombok.Getter;
import roomescape.core.domain.theme.exception.ThemeException;

import java.util.Objects;
import java.util.Optional;

@Getter
public class Theme {

    private final ThemeId id;
    private final String name;
    private final String description;
    private final String thumbnail;

    @Builder
    private Theme(
            final ThemeId id,
            final String name,
            final String description,
            final String thumbnail
    ) {
        if (Objects.isNull(name)) {
            throw ThemeException.nullField("name");
        }
        if (Objects.isNull(description)) {
            throw ThemeException.nullField("description");
        }
        if (Objects.isNull(thumbnail)) {
            throw ThemeException.nullField("thumbnail");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Optional<Long> getIdValue() {
        if (Objects.isNull(this.id)) {
            return Optional.empty();
        }
        return Optional.of(this.id.value());
    }
}
