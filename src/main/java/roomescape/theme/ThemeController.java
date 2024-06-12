package roomescape.theme;

import jakarta.validation.Valid;
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

    @GetMapping
    public List<ThemeResponse> getThemes() {
        return themeService.findThemes();
    }

    @PostMapping
    public ResponseEntity<ThemeResponse> saveTheme(@RequestBody @Valid ThemeRequest request) {
        Long id = themeService.saveThemes(request);
        return ResponseEntity.created(URI.create("/themes/" + id)).body(themeService.findTheme(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable String id) {
        themeService.deleteTheme(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
