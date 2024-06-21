package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.theme.ThemeResponse;
import roomescape.dto.theme.create.ThemeCreateRequest;
import roomescape.dto.theme.create.ThemeCreateResponse;
import roomescape.exception.custom.DuplicatedThemeNameException;
import roomescape.service.ThemeService;

import java.util.List;

@Controller
@RequestMapping("/themes")
public class ThemeController {

    private ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    /**
     * 테마 관리 페이지의 테마
     * 순서 제목 설명 썸네일URL
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ThemeResponse>> findThemes() {
        return ResponseEntity.ok().body(themeService.findThemes());
    }

    @PostMapping
    public ResponseEntity<ThemeCreateResponse> createTheme(@Valid @RequestBody ThemeCreateRequest request) {
        ThemeCreateResponse response = themeService.createTheme(request);
        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(DuplicatedThemeNameException.class)
    public ResponseEntity<String> handleDuplicatedThemeNameException(DuplicatedThemeNameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
