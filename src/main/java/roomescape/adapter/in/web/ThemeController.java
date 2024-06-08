package roomescape.adapter.in.web;

import static roomescape.adapter.mapper.ThemeMapper.mapToDomain;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.adapter.in.web.dto.ThemeCommand;
import roomescape.adapter.in.web.dto.ThemeResponse;
import roomescape.adapter.mapper.ThemeMapper;
import roomescape.application.port.in.ThemeUseCase;

@RestController
@RequestMapping("/themes")
public class ThemeController {

  private final ThemeUseCase themeUseCase;

  public ThemeController(ThemeUseCase themeUseCase) {
    this.themeUseCase = themeUseCase;
  }

  @GetMapping
  public ResponseEntity<List<ThemeResponse>> getThemes() {
    List<ThemeResponse> themes = themeUseCase.retrieveThemes()
                                             .stream()
                                             .map(ThemeMapper::mapToResponse)
                                             .toList();

    return new ResponseEntity<>(themes, HttpStatus.OK);

  }

  @PostMapping
  public ResponseEntity<ThemeResponse> createReservation(@RequestBody ThemeCommand themeCommand) {
    return ResponseEntity.ok()
                         .body(ThemeMapper.mapToResponse(themeUseCase.registerTheme(mapToDomain(themeCommand))));
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  public void deleteReservation(@PathVariable("id") Long id) {
    themeUseCase.deleteTheme(id);
  }
}
