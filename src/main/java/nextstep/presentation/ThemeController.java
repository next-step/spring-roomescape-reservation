package nextstep.presentation;

import java.net.URI;
import java.util.List;
import nextstep.application.ThemeService;
import nextstep.presentation.dto.ThemeRequest;
import nextstep.presentation.dto.ThemeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ThemeRequest request) {
        Long themeId = themeService.create(request);
        return ResponseEntity.created(URI.create("/themes/" + themeId)).build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> checkAll() {
        List<ThemeResponse> responses = themeService.checkAll();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        themeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
