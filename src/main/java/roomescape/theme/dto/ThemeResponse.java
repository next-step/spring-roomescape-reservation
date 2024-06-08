package roomescape.theme.dto;

public record ThemeResponse(Long id, String name, String description, String thumbnail) {

    public static ThemeResponse of(Long id, String name, String description, String thumbnail) {
        return new ThemeResponse(id, name, description, thumbnail);
    }
}
