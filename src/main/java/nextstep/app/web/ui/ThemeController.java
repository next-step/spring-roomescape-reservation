package nextstep.app.web.ui;

import nextstep.app.web.dto.ThemeCreateWebRequest;
import nextstep.app.web.dto.ThemeWebResponse;
import nextstep.core.theme.Theme;
import nextstep.core.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/themes")
@RestController
public class ThemeController {
    private final ThemeService service;

    public ThemeController(ThemeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ThemeCreateWebRequest request) {
        Theme theme = service.save(request);
        return ResponseEntity
                .created(URI.create("/themes/" + theme.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeWebResponse>> list() {
        List<Theme> themes = service.list();
        return ResponseEntity.ok(
                themes.stream()
                        .map(ThemeWebResponse::from)
                        .toList()
        );
    }
}
