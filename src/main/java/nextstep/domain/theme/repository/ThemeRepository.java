package nextstep.domain.theme.repository;

import java.util.List;
import java.util.Optional;
import nextstep.domain.theme.ThemeEntity;

public interface ThemeRepository {

  ThemeEntity save(ThemeEntity themes);

  List<ThemeEntity> findAllThemes();

  void deleteById(Long id);

  Optional<ThemeEntity> findTheme(Long id);
}
