package roomescape.domain.theme.vo;

public record ThemeId(Long id) {

    public static ThemeId empty() {
        return new ThemeId(null);
    }
}
