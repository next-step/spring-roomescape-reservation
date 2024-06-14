package roomescape.application.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.api.dto.request.CreateThemeRequest;
import roomescape.application.api.dto.response.CreateThemeResponse;
import roomescape.application.service.ThemeService;
import roomescape.domain.theme.Theme;

import java.net.URI;

@RestController
public class ThemeCommandApi {

    private final ThemeService themeService;

    public ThemeCommandApi(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/themes")
    public ResponseEntity<CreateThemeResponse> createTheme(@RequestBody CreateThemeRequest createThemeRequest) {
        Theme theme = themeService.createTheme(createThemeRequest.toCreateThemeCommand());

        return ResponseEntity
                .created(URI.create(String.format("/themes/%d", theme.getId())))
                .body(CreateThemeResponse.from(theme));
    }
}
