package nextstep.schedule.domain;

import java.util.List;

public interface ThemeRepository {

    Theme save(Theme theme);

    Theme findById(Long id);

    List<Theme> findAll();

    int deleteById(Long id);
}
