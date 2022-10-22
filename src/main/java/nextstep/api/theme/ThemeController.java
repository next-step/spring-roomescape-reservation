package nextstep.api.theme;

import nextstep.api.theme.dto.ThemeCreateRequest;
import nextstep.domain.theme.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> create(@RequestBody ThemeCreateRequest request) {
        Long id = themeService.create(request.toEntity());
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }
}
