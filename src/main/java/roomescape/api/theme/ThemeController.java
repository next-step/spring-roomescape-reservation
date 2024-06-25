package roomescape.api.theme;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.theme.CreateTheme;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeService;

@RestController
@RequestMapping("themes")
public class ThemeController {
  private final ThemeService service;

  public ThemeController(ThemeService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Theme create(@RequestBody CreateTheme theme) {
    return service.create(theme);
  }

  @GetMapping
  public List<Theme> findAll() {
    return service.findAll();
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") long id) {
    service.delete(id);
  }
}
