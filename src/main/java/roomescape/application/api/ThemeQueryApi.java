package roomescape.application.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.api.dto.response.FindAllThemesResponse;
import roomescape.application.service.ThemeService;
import roomescape.domain.theme.Themes;

import java.util.List;

@RestController
public class ThemeQueryApi {

    private final ThemeService themeService;

    public ThemeQueryApi(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/themes")
    public ResponseEntity<List<FindAllThemesResponse>> findAll() {
        Themes themes = themeService.findAll();

        return ResponseEntity.ok(FindAllThemesResponse.toFindAllThemesResponses(themes));
    }
}
