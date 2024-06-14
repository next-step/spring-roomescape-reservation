package roomescape.application.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.api.dto.request.CreateThemeRequest;
import roomescape.application.api.dto.response.CreateThemeResponse;
import roomescape.application.service.ThemeService;
import roomescape.application.service.command.DeleteThemeCommand;
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

    @DeleteMapping("/themes/{themeId}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long themeId) {
        themeService.deleteTheme(new DeleteThemeCommand(themeId));

        return ResponseEntity.noContent().build();
    }
}
