package nextstep.application.themes;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.application.themes.dto.Theme;
import nextstep.application.themes.dto.ThemeDeleteValidationDto;
import nextstep.application.themes.dto.ThemeRes;
import nextstep.domain.theme.ThemeEntity;
import nextstep.domain.theme.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ThemeService {

  private final ThemeRepository repository;
  private final ThemeQueryService themeQueryService;

  private final ThemeDeletePolicy deletePolicy;

  @Transactional
  public Long create(Theme req) {
    var themes = ThemeEntity.builder()
        .name(req.name())
        .desc(req.desc())
        .price(req.price())
        .build();
    var entity = repository.save(themes);
    return entity.getId();
  }

  public List<ThemeRes> getThemes() {
    return themeQueryService.getThemes();
  }

  public void delete(Long id) {
    deletePolicy.checkValid(ThemeDeleteValidationDto.builder()
        .id(id)
        .build());
    repository.deleteById(id);
  }

  public Optional<ThemeRes> getTheme(Long themeId) {
    return themeQueryService.getTheme(themeId);
  }
}
