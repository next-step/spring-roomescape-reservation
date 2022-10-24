package nextstep.application.themes;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.application.themes.dto.ThemeRes;
import nextstep.domain.theme.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThemeQueryService {

  private final ThemeRepository repository;

  public List<ThemeRes> getThemes() {
    var themes = repository.findAllThemes();
    return themes.stream()
        .map(it -> ThemeRes.builder()
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

  public Optional<ThemeRes> getTheme(Long themeId) {
    var entity = repository.findTheme(themeId);
    if (entity.isPresent()) {
      var theme = entity.get();
      return Optional.ofNullable(ThemeRes.builder()
          .id(theme.getId())
          .name(theme.getName())
          .desc(theme.getDesc())
          .price(theme.getPrice())
          .build());
    }
    return Optional.empty();
  }
}
