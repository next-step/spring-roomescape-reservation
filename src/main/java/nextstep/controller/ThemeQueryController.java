package nextstep.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.controller.dto.ThemeViewResponse.Theme;
import nextstep.domain.theme.ThemeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ThemeQueryController {

  ThemeRepository themeRepository;

  @GetMapping
  public ResponseEntity<List<Theme>> getThemes() {
    List<nextstep.domain.theme.Theme> themes = themeRepository.findAll();
    List<Theme> responses = themes.stream()
        .map(Theme::of)
        .toList();
    return ResponseEntity.ok().body(responses);
  }
}
