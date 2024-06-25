package roomescape.api.theme;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.theme.CreateTheme;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeRepository;

@RestController
@RequestMapping("themes")
public class ThemeController {
  private final ThemeRepository themeRepository;

  public ThemeController(ThemeRepository themeRepository) {
    this.themeRepository = themeRepository;
  }

  @PostMapping
  public Theme create(@RequestBody CreateTheme theme) {
    return themeRepository.create(theme);
  }
}
