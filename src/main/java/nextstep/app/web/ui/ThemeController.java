package nextstep.app.web.ui;

import nextstep.app.web.dto.ThemeCreateRequest;
import nextstep.core.Theme;
import nextstep.core.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/themes")
@RestController
public class ThemeController {
    private final ThemeService service;

    public ThemeController(ThemeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ThemeCreateRequest request) {
        Theme theme = service.save(request);
        return ResponseEntity
                .created(URI.create("/themes/" + theme.getId()))
                .build();
    }
}
