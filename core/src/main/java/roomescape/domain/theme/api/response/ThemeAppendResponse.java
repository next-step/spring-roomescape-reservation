package roomescape.domain.theme.api.response;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.theme.domain.Theme;

@Getter
public final class ThemeAppendResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final String thumbnail;

    @Builder
    private ThemeAppendResponse(
            Long id,
            String name,
            String description,
            String thumbnail
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public static ThemeAppendResponse fromTheme(final Theme theme) {
        return ThemeAppendResponse.builder()
                .id(theme.getIdValue().orElse(null))
                .name(theme.getName())
                .description(theme.getDescription())
                .thumbnail(theme.getThumbnail())
                .build();
    }
}
