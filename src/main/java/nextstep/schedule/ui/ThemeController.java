package nextstep.schedule.ui;

import java.net.URI;
import java.util.List;
import nextstep.schedule.application.ThemeService;
import nextstep.schedule.ui.request.ThemeCreateRequest;
import nextstep.schedule.ui.response.ThemeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody ThemeCreateRequest request) {
        ThemeResponse response = themeService.create(request);
        return ResponseEntity.created(URI.create("/themes/" + response.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        return ResponseEntity.ok(themeService.findAll());
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long themeId) {
        themeService.deleteById(themeId);
        return ResponseEntity.noContent().build();
    }
}
