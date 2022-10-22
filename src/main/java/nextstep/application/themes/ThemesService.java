package nextstep.application.themes;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.themes.dto.Themes;
import nextstep.application.themes.dto.ThemesRes;
import nextstep.domain.themes.ThemesEntity;
import nextstep.domain.themes.repository.ThemesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ThemesService {

  private final ThemesRepository repository;

  @Transactional
  public Long create(Themes req) {
    var themes = ThemesEntity.builder()
        .name(req.name())
        .desc(req.desc())
        .price(req.price())
        .build();
    var entity = repository.save(themes);
    return entity.getId();
  }

  public List<ThemesRes> findAllThemes() {
    var themes = repository.findAllThemes();
    return themes.stream()
        .map(it -> ThemesRes.builder()
            .id(it.getId())
            .name(it.getName())
            .desc(it.getDesc())
            .price(it.getPrice())
            .build())
        .toList();
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}
