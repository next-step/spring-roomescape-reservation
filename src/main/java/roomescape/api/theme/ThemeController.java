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
  public ThemeListItemResponse create(@RequestBody CreateThemeRequest theme) {
    return ThemeListItemResponse.from(service.create(theme.toDomain()));
  }

  @GetMapping
  public List<ThemeListItemResponse> findAll() {
    return service.findAll().stream().map(ThemeListItemResponse::from).toList();
  }

  @GetMapping("dropdown")
  public List<ThemeDropdownItemResponse> findAllDropdown() {
    return service.findAllSummaries().stream().map(ThemeDropdownItemResponse::from).toList();
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") long id) {
    service.delete(id);
  }
}
