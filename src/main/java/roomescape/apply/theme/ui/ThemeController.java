package roomescape.apply.theme.ui;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.apply.theme.application.ThemeDeleter;
import roomescape.apply.theme.application.ThemeFinder;
import roomescape.apply.theme.application.ThemeSaver;
import roomescape.apply.theme.ui.dto.ThemeRequest;
import roomescape.apply.theme.ui.dto.ThemeResponse;

import java.util.List;

@RestController
@RequestMapping("/themes")
public record ThemeController(ThemeSaver themeSaver,
                              ThemeFinder themeFinder,
                              ThemeDeleter themeDeleter) {

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> findAllThemes() {
        List<ThemeResponse> responses = themeFinder.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ThemeResponse> createTheme(@RequestBody ThemeRequest request) {
        ThemeResponse themeResponse = themeSaver.saveThemeBy(request);
        return ResponseEntity.ok(themeResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<Void> deleteTime(@PathVariable("id") long id) {
        themeDeleter.deleteThemeBy(id);
        return ResponseEntity.ok().build();
    }
}
