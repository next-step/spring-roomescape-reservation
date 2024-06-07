package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ThemeDao;
import roomescape.domain.Theme;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ThemeController {
    private ThemeDao themeDao;

    public ThemeController(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    @PostMapping("/themes")
    public ResponseEntity<Theme> createTheme(@RequestBody Theme theme) {
        Theme newTheme = themeDao.save(theme);
        return ResponseEntity.ok().body(newTheme);
    }

    @GetMapping("/themes")
    public ResponseEntity<List<Theme>> list() {
        return ResponseEntity.ok(themeDao.findAll());
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
