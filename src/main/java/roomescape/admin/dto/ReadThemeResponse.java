package roomescape.admin.dto;

import roomescape.admin.entity.Theme;

import java.util.List;
import java.util.stream.Collectors;

public record ReadThemeResponse(Long id,
                                String name,
                                String description,
                                String thumbnail) {
    private ReadThemeResponse(Theme theme){
        this(theme.getId(),
                theme.getName(),
                theme.getDescription(),
                theme.getthumbnail());
    }

    public static ReadThemeResponse from(Theme theme) {
        return new ReadThemeResponse(theme);
    }

    public static List<ReadThemeResponse> from(List<Theme> themes) {
        return themes.stream()
                .map(ReadThemeResponse::new)
                .collect(Collectors.toList());
    }


}
