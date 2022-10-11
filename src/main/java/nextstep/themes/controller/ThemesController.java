package nextstep.themes.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.themes.Themes;
import nextstep.themes.controller.response.ThemesFindResponse;
import nextstep.themes.service.ThemesService;
import nextstep.themes.controller.request.ThemesCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
public class ThemesController {

    private final ThemesService themesService;

    public ThemesController(ThemesService themesService) {
        this.themesService = themesService;
    }

    @PostMapping
    public ResponseEntity createThemes(@RequestBody ThemesCreateRequest request) {
        long id = themesService.createThemes(request.getName(), request.getDesc(), request.getPrice());
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ThemesFindResponse>> findThemes() {
        List<ThemesFindResponse> response = themesService.findThemes().stream()
                .map(ThemesFindResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeThemes(@PathVariable Long id) {
        themesService.removeThemes(id);
        return ResponseEntity.noContent().build();
    }
}
