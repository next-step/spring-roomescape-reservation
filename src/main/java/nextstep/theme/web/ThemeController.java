package nextstep.theme.web;

import java.net.URI;
import java.util.List;
import nextstep.theme.domain.Theme;
import nextstep.theme.persistence.ThemeDao;
import nextstep.theme.web.reqeust.MakeThemeRequest;
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
public class ThemeController {

    private final ThemeDao themeDao;

    public ThemeController(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    @PostMapping
    ResponseEntity<Void> makeTheme(@RequestBody MakeThemeRequest requestBody) {
        Long id = themeDao.insert(requestBody.toTheme());
        URI locationUri = URI.create("/themes/" + id);
        return ResponseEntity.created(locationUri)
            .build();
    }

    @GetMapping
    ResponseEntity<List<Theme>> listThemes() {
        List<Theme> themes = themeDao.findAll();
        return ResponseEntity.ok(themes);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeDao.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
