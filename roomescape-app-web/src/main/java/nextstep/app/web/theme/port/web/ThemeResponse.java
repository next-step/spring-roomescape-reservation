package nextstep.app.web.theme.port.web;

import nextstep.domain.theme.domain.model.Theme;

import java.util.List;

public record ThemeResponse(Long id,
                            String name,
                            String desc,
                            Long price) {
    public static List<ThemeResponse> listFrom(List<Theme> themes) {
        return themes.stream()
                .map(ThemeResponse::from)
                .toList();
    }

    private static ThemeResponse from(Theme theme) {
        return new ThemeResponse(theme.id(), theme.name(), theme.description(), theme.price());
    }
}
