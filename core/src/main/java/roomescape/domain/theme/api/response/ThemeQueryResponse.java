package roomescape.domain.theme.api.response;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.theme.domain.Theme;

import java.util.List;

@Getter
public class ThemeQueryResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final String thumbnail;

    @Builder
    private ThemeQueryResponse(
            final Long id,
            final String name,
            final String description,
            final String thumbnail
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public static List<ThemeQueryResponse> fromThemes(final List<Theme> themes) {
        return themes.stream()
                .map(ThemeQueryResponse::fromTheme)
                .toList();
    }

    public static ThemeQueryResponse fromTheme(final Theme theme) {
        return ThemeQueryResponse.builder()
                .id(theme.getIdValue().orElse(null))
                .name(theme.getName())
                .description(theme.getDescription())
                .thumbnail(theme.getThumbnail())
                .build();
    }
}
