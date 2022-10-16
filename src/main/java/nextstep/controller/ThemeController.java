package nextstep.controller;

import nextstep.dto.ThemeCreateRequest;
import nextstep.dto.ThemeFindAllResponse;
import nextstep.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ThemeCreateRequest themeCreateRequest) {
        Long themeId = themeService.createTheme(themeCreateRequest);
        return ResponseEntity.created(URI.create("/themes/" + themeId)).build();
    }

    @GetMapping
    public ResponseEntity<ThemeFindAllResponse> findAll() {
        ThemeFindAllResponse themeFindallResponse = themeService.findAllThemes();
        return ResponseEntity.ok(themeFindallResponse);
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> delete(@PathVariable Long themeId) {
        themeService.deleteTheme(themeId);
        return ResponseEntity.noContent().build();
    }
}
