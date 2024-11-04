package roomescape.domain.theme.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.theme.api.request.ThemeAppendHttpRequest;
import roomescape.domain.theme.api.response.ThemeAppendResponse;
import roomescape.domain.theme.api.response.ThemeQueryResponse;
import roomescape.domain.theme.application.ThemeService;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeId;

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
