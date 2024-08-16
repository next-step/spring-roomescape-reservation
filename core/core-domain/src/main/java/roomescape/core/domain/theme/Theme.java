package roomescape.core.domain.theme;

import lombok.Builder;
import lombok.Getter;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.theme.exception.ThemeException;

import java.util.Objects;
import java.util.Optional;

@Getter
public class Theme {

    private final ThemeId id;
    private final String name;
    private final String description;
    private final String thumbnail;
    private final ActiveStatus activeStatus;

    @Builder
    private Theme(
            final ThemeId id,
            final String name,
            final String description,
            final String thumbnail,
            final ActiveStatus activeStatus
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
        if (Objects.isNull(activeStatus)) {
            throw ThemeException.nullField("activeStatus");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.activeStatus = activeStatus;
    }

    public static Theme defaultOf(
            final String name,
            final String description,
            final String thumbnail
    ) {
        return Theme.builder()
                .name(name)
                .description(description)
                .thumbnail(thumbnail)
                .activeStatus(ActiveStatus.ACTIVE)
                .build();
    }

    public Optional<Long> getIdValue() {
        if (Objects.isNull(this.id)) {
            return Optional.empty();
        }
        return Optional.of(this.id.value());
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final Theme theme = (Theme) object;
        return Objects.equals(id, theme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
