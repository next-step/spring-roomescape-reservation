package nextstep.theme;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody ThemeCreateRequest request) {
        return ResponseEntity.created(URI.create("/themes/1")).build();
    }
}
