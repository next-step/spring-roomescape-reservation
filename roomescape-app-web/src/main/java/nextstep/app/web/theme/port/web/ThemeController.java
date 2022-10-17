package nextstep.app.web.theme.port.web;

import nextstep.app.web.theme.application.dto.CreateThemeResult;
import nextstep.app.web.theme.application.usecase.CreateThemeUseCase;
import nextstep.app.web.theme.application.usecase.DeleteThemeUseCase;
import nextstep.app.web.theme.port.query.ThemeQuery;
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
public class ThemeController {
    private final CreateThemeUseCase createThemeUseCase;
    private final DeleteThemeUseCase deleteThemeUseCase;
    private final ThemeQuery themeQuery;

    public ThemeController(CreateThemeUseCase createThemeUseCase, DeleteThemeUseCase deleteThemeUseCase, ThemeQuery themeQuery) {
        this.createThemeUseCase = createThemeUseCase;
        this.deleteThemeUseCase = deleteThemeUseCase;
        this.themeQuery = themeQuery;
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> create(@RequestBody ThemeCreateRequest request) {
        CreateThemeResult createThemeResult = createThemeUseCase.create(request.toCommand());
        return ResponseEntity.created(URI.create("/themes/" + createThemeResult.id())).build();
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteThemeUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeResponse>> findAll() {
        return ResponseEntity.ok(ThemeResponse.listFrom(themeQuery.findAll()));
    }
}
