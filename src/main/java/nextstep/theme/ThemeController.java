package nextstep.theme;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody ThemeCreateRequest request) {
        Theme theme = this.themeService.createTheme(request);
        return ResponseEntity.created(URI.create("/themes/" + theme.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        List<ThemeResponse> responses = this.themeService.getThemes();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> deleteTheme(@PathVariable("themeId")Long themeId) {
        this.themeService.deleteTheme(themeId);
        return ResponseEntity.noContent().build();
    }
}
