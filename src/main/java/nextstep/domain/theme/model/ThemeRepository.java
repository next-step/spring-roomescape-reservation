package nextstep.domain.theme.model;

import java.util.List;

public interface ThemeRepository {
    Long create(Theme theme);
    List<Theme> findAll();
}
