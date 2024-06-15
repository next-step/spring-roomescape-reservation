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
@RequestMapping("/themes")
public class ThemeController {
    private ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping()
    public ResponseEntity<List<ThemeResponse>> read() {
        List<ThemeResponse> themes = themeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(themes);
    }

    @PostMapping()
    public ResponseEntity<ThemeResponse> create(@RequestBody ThemeRequest request) {
        long themeId = themeService.add(request);
        ThemeResponse theme = themeService.findOne(themeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(theme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        themeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
