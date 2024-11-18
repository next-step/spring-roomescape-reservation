package roomescape.domain.theme.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@Getter
public class Theme {

    private final ThemeId id;
    private final String name;
    private final String description;
    private final String thumbnail;
    private final boolean deleted;

    @Builder
    private Theme(
            final ThemeId id,
            final String name,
            final String description,
            final String thumbnail,
            final boolean deleted
    ) {
        verifyThemeName(name);

        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.deleted = deleted;
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
                .deleted(false)
                .build();
    }

    public Theme delete() {
        return Theme.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .thumbnail(this.thumbnail)
                .deleted(true)
                .build();
    }

    private void verifyThemeName(final String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or whitespace");
        }
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
