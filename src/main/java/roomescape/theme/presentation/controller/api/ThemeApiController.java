package roomescape.theme.presentation.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.theme.domain.Theme;
import roomescape.theme.presentation.dto.ThemeRequest;
import roomescape.theme.presentation.dto.ThemeResponse;
import roomescape.theme.service.ThemeService;

import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeApiController {

    private final ThemeService themeService;

    public ThemeApiController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<ThemeResponse> save(@RequestBody ThemeRequest request) {
        Theme theme = themeService.save(new Theme(null, request.getName(), request.getDescription(), request.getThumbnail()));
        return ResponseEntity.ok().body(ThemeResponse.builder().id(theme.getId()).name(theme.getName()).description(theme.getName()).thumbnail(theme.getThumbnail()).build());
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        List<Theme> themes = themeService.findAll();
        List<ThemeResponse> responses = themes.stream().map(theme -> ThemeResponse.builder().
                id(theme.getId())
                .name(theme.getName())
                .description(theme.getDescription())
                .thumbnail(theme.getThumbnail())
                .build()
        ).toList();
        return ResponseEntity.ok(responses);
    }
}
