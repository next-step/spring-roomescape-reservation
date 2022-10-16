package nextstep.dto;

import nextstep.domain.Theme;

import java.util.List;
import java.util.stream.Collectors;

public class ThemeFindAllResponse {
    private List<ThemeFindResponse> themes;

    public ThemeFindAllResponse() {
    }

    public ThemeFindAllResponse(List<ThemeFindResponse> themes) {
        this.themes = themes;
    }

    public List<ThemeFindResponse> getThemes() {
        return themes;
    }

    public static ThemeFindAllResponse from(List<Theme> themes) {
        List<ThemeFindResponse> themeFindResponses = themes.stream()
                .map(ThemeFindResponse::from)
                .collect(Collectors.toList());
        return new ThemeFindAllResponse(themeFindResponses);
    }
}
