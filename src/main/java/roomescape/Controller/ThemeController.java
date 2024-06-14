package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ThemeRequest;
import roomescape.DTO.ThemeResponse;
import roomescape.Service.ThemeService;

import java.util.List;

@RestController
public class ThemeController {
    private final String URI = "themes";
    private ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping(URI)
    public ResponseEntity<List<ThemeResponse>> read() {
        List<ThemeResponse> themes = themeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(themes);
    }

    @PostMapping(URI)
    public ResponseEntity<ThemeResponse> create(@RequestBody ThemeRequest request) {
        long themeId = themeService.add(request);
        ThemeResponse theme = themeService.findOne(themeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(theme);
    }

    @DeleteMapping(URI + "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        themeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
