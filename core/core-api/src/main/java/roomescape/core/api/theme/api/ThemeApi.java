package roomescape.core.api.theme.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.core.api.theme.api.request.ThemeAppendHttpRequest;
import roomescape.core.api.theme.api.response.ThemeAppendResponse;
import roomescape.core.api.theme.api.response.ThemeQueryResponse;
import roomescape.core.api.theme.service.ThemeService;
import roomescape.core.domain.theme.Theme;

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
}
