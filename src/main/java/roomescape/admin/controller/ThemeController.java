package roomescape.admin.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.admin.dto.ReadThemeResponse;
import roomescape.admin.dto.SaveThemeRequest;
import roomescape.admin.service.ThemeService;

import java.util.List;


@RestController
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService){
        this.themeService = themeService;
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ReadThemeResponse>> readTheme(){
        List<ReadThemeResponse> response = this.themeService.readTheme();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/themes")
    public ResponseEntity<ReadThemeResponse> saveTheme(@Valid @RequestBody SaveThemeRequest saveThemeRequest){
        ReadThemeResponse response = this.themeService.saveTheme(saveThemeRequest);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable(name = "id") Long id){
        this.themeService.deleteTheme(id);

        return ResponseEntity.ok().build();
    }
}