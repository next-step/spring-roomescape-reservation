package nextstep.domain;

import java.util.List;

public interface ThemeRepository {

    Theme save(Theme theme);

    List<Theme> findAll();

    int deleteById(Long id);
}
