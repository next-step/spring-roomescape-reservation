package nextstep.core.theme.in;

import java.util.List;

public interface ThemeUseCase {
    ThemeResponse create(ThemeCreateRequest request);

    List<ThemeResponse> list();

    void delete(String themeId);
}
