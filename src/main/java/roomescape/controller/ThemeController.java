package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ThemeRq;
import roomescape.dto.ThemeRs;
import roomescape.service.ThemeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    // 테마 조회
    @GetMapping
    public ResponseEntity<List<ThemeRs>> getThemes() {
        List<ThemeRs> themes = themeService.getAllThemes();
        return ResponseEntity.ok().body(themes);
    }

    // 테마 추가
    @PostMapping
    public ResponseEntity<ThemeRs> addTheme(@RequestBody ThemeRq themeRq) {
        ThemeRs themeRs = themeService.addTheme(themeRq);
        return ResponseEntity.created(URI.create("/themes/" + themeRs.getId())).body(themeRs);
    }

    // 테마 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }

}
