package nextstep.app.web.theme;

import nextstep.app.web.theme.adapter.in.ThemeService;
import nextstep.core.theme.in.ThemeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/themes")
@RestController
class ThemeController {
    private final ThemeService service;

    public ThemeController(ThemeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ThemeCreateWebRequest request) {
        ThemeResponse theme = service.create(request.to());
        return ResponseEntity
                .created(URI.create("/themes/" + theme.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeWebResponse>> list() {
        List<ThemeResponse> themes = service.list();
        return ResponseEntity.ok(
                themes.stream()
                        .map(ThemeWebResponse::from)
                        .toList()
        );
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> delete(@PathVariable String themeId) {
        service.delete(themeId);
        return ResponseEntity.noContent().build();
    }
}
