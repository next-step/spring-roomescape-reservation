package nextstep.roomescape.web;

import java.net.URI;
import java.util.List;
import nextstep.roomescape.service.ThemeService;
import nextstep.roomescape.web.dto.ThemeRequest;
import nextstep.roomescape.web.dto.ThemeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/themes")
    public ResponseEntity<URI> create(@RequestBody ThemeRequest request) {
        final int id = themeService.create(request);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeResponse>> findAll() {
        return ResponseEntity.ok(themeService.findAll());
    }

    @DeleteMapping("/themes")
    public ResponseEntity<Void> delete(Integer id) {
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
