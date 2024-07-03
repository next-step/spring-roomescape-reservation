package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.model.ThemeCreateRequestDto;
import roomescape.model.ThemeCreateResponseDto;
import roomescape.model.ThemeReadResponseDto;
import roomescape.service.ThemeAdminService;

import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeAdminApiController {
    private final ThemeAdminService themeAdminService;

    public ThemeAdminApiController(ThemeAdminService themeAdminService) {
        this.themeAdminService = themeAdminService;
    }

    @PostMapping
    public ResponseEntity<ThemeCreateResponseDto> createTheme(@RequestBody ThemeCreateRequestDto themeCreateRequestDto) {
        ThemeCreateResponseDto themeCreateResponseDto = themeAdminService.createTheme(themeCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(themeCreateResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ThemeReadResponseDto>> readTheme() {
        List<ThemeReadResponseDto> themeReadResponseDtos = themeAdminService.readThemes();
        return ResponseEntity.ok().body(themeReadResponseDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeAdminService.deleteTheme(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
