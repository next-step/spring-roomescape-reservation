package roomescape.theme;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/themes")
public class ThmemController {
	private final ThemeService themeService;

	public ThmemController(ThemeService themeService) {
		this.themeService = themeService;
	}

	@GetMapping
	public ResponseEntity<List<ThemeResponse>> getThemes() {
		return ResponseEntity.ok().body(themeService.findThemes());
	}

	@PostMapping
	public ResponseEntity<ThemeResponse> saveThemes(@RequestBody ThemeRequest request) {
		Long id = themeService.saveThemes(request);
		return ResponseEntity.created(URI.create("/themes/"+id)).body(themeService.findTheme(id));
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteThemes(@PathVariable String id) {
		themeService.deleteThemes(Long.parseLong(id));
		return ResponseEntity.noContent().build();
	}
}
