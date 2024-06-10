package roomescape.controller;

import java.util.List;

import roomescape.controller.dto.ThemeRequest;
import roomescape.controller.dto.ThemeResponse;
import roomescape.service.ThemeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static roomescape.controller.dto.ThemeRequest.validateThemeRequest;

@RestController
@RequestMapping("/themes")
public class ThemeController {

	private final ThemeService themeService;

	ThemeController(ThemeService themeService) {
		this.themeService = themeService;
	}

	@GetMapping
	public ResponseEntity<List<ThemeResponse>> getThemes() {
		return ResponseEntity.ok().body(this.themeService.getThemes());
	}

	@PostMapping
	public ResponseEntity<ThemeResponse> create(@RequestBody ThemeRequest request) {
		validateThemeRequest(request);
		return ResponseEntity.ok().body(this.themeService.create(request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") long id) {
		this.themeService.delete(id);
		return ResponseEntity.ok().build();
	}

}
