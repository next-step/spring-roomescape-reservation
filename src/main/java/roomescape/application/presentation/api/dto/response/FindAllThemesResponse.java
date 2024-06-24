package roomescape.application.presentation.api.dto.response;

import roomescape.domain.theme.Theme;
import roomescape.domain.theme.Themes;

import java.util.List;
import java.util.stream.Collectors;

public class FindAllThemesResponse {

    private final Long id;

    private final String name;

    private final String description;

    private final String thumbnail;

    public FindAllThemesResponse(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    private static FindAllThemesResponse from(Theme theme) {
        return new FindAllThemesResponse(
                theme.getId(),
                theme.getThemeName(),
                theme.getThemeDescription(),
                theme.getThemeThumbnail()
        );
    }

    public static List<FindAllThemesResponse> toFindAllThemesResponses(Themes themes) {
        return themes.getThemes().stream()
                .map(FindAllThemesResponse::from)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
