package nextstep.web.endpoint.theme.controller;

import lombok.extern.slf4j.Slf4j;
import nextstep.web.endpoint.theme.request.ThemeCreateRequest;
import nextstep.web.endpoint.theme.response.ThemeResponse;
import nextstep.web.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> create(@RequestBody ThemeCreateRequest request) {
        log.info("테마 생성 요청. request= {}", request);
        Long id = themeService.create(request);
        URI location = URI.create("/reservations/" + id);
        log.info("테마 생성 응답. location= {}", location);

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeResponse>> findAll() {
        log.info("테마 목록 조회 요청.");
        List<ThemeResponse> response = themeService.findAll();
        log.info("테마 목록 조회 응답. response= {}", response);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/themes/{themeId}")
    public ResponseEntity<Void> cancel(@PathVariable Long themeId) {
        log.info("테마 삭제 요청. themeId= {}", themeId);
        themeService.deleteById(themeId);
        log.info("테마 삭제 성공.");

        return ResponseEntity.noContent().build();
    }
}
