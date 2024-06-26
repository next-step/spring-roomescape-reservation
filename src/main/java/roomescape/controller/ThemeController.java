package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Theme;
import roomescape.service.ThemeService;

import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public Long create(@RequestBody Theme theme) {
        return themeService.addTheme(theme);
    }

    @GetMapping
    public List<Theme> read() {
        return themeService.lookUpTheme();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        themeService.deleteTheme(id);
    }
}
