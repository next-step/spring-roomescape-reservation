package nextstep.ui;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.themes.ThemesService;
import nextstep.application.themes.dto.Themes;
import nextstep.application.themes.dto.ThemesRes;
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
public class ThemesController {

  private final ThemesService service;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody Themes themes) {
    var id = service.create(themes);
    return ResponseEntity.created(URI.create("/themes/" + id)).build();
  }

  @GetMapping
  public ResponseEntity<List<ThemesRes>> getAllThemes() {
    var themes = service.findAllThemes();
    return ResponseEntity.ok(themes);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

}
