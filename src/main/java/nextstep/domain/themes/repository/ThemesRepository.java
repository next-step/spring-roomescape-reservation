package nextstep.domain.themes.repository;

import java.util.List;
import nextstep.domain.themes.ThemesEntity;

public interface ThemesRepository {

  ThemesEntity save(ThemesEntity themes);

  List<ThemesEntity> findAllThemes();

  void deleteById(Long id);
}
