package nextstep.api.theme;

import nextstep.api.theme.dto.ThemeCreateRequest;
import nextstep.api.theme.dto.ThemeResponse;
import nextstep.domain.theme.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeResponse>> findAll() {
        List<ThemeResponse> responses = themeService.findAll().stream()
            .map(ThemeResponse::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        themeService.remove(id);

        return ResponseEntity.noContent().build();
    }
}
