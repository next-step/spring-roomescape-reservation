package nextstep.ui;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.themes.ThemeService;
import nextstep.application.themes.dto.Theme;
import nextstep.application.themes.dto.ThemeRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {

  private final ThemeService service;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody Theme theme) {
    var id = service.create(theme);
    return ResponseEntity.created(URI.create("/themes/" + id)).build();
  }

  @GetMapping
  public ResponseEntity<List<ThemeRes>> getThemes() {
    var themes = service.getThemes();
    return ResponseEntity.ok(themes);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

}
