package roomescape.domain.theme;

import java.util.List;

public class Themes {

    private final List<Theme> themes;

    public Themes(List<Theme> themes) {
        this.themes = themes;
    }

    public List<Theme> getThemes() {
        return List.copyOf(themes);
    }
}
