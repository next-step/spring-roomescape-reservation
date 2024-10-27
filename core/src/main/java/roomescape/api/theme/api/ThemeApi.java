package roomescape.api.theme.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.api.theme.api.request.ThemeAppendHttpRequest;
import roomescape.api.theme.api.response.ThemeAppendResponse;
import roomescape.api.theme.api.response.ThemeQueryResponse;
import roomescape.api.theme.application.ThemeService;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeId;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ThemeApi {

    private final ThemeService themeService;

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeQueryResponse>> getThemes() {
        final List<Theme> themes = themeService.findAll();

        final List<ThemeQueryResponse> response = ThemeQueryResponse.fromThemes(themes);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/themes")
    public ResponseEntity<ThemeAppendResponse> appendTheme(
            @RequestBody ThemeAppendHttpRequest request
    ) {
        final Theme theme = themeService.appendTheme(request.toServiceRequest());
        return ResponseEntity.ok().body(ThemeAppendResponse.fromTheme(theme));
    }

    @DeleteMapping("/themes/{themeId}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long themeId) {
        themeService.deleteTheme(new ThemeId(themeId));
        return ResponseEntity.noContent().build();
    }
}
