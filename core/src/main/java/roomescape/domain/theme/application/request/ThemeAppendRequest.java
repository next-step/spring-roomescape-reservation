package roomescape.domain.theme.application.request;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.theme.domain.Theme;

@Getter
public class ThemeAppendRequest {

    private final String name;
    private final String description;
    private final String thumbnail;

    @Builder
    private ThemeAppendRequest(final String name, final String description, final String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Theme toTheme() {
        return Theme.defaultOf(
                this.name,
                this.description,
                this.thumbnail
        );
    }
}
