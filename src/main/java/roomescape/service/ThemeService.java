package roomescape.service;

import java.util.List;

import roomescape.controller.dto.ThemeRequest;
import roomescape.controller.dto.ThemeResponse;
import roomescape.domain.Theme;
import roomescape.repository.ThemeRepository;

import org.springframework.stereotype.Service;

@Service
public class ThemeService {

	private final ThemeRepository themeRepository;

	ThemeService(ThemeRepository themeRepository) {
		this.themeRepository = themeRepository;
	}

	public List<ThemeResponse> getThemes() {
		return this.themeRepository.findAll().stream().map(ThemeResponse::from).toList();
	}

	public ThemeResponse create(ThemeRequest request) {
		var theme = Theme.builder()
			.name(request.name())
			.description(request.description())
			.thumbnail(request.thumbnail())
			.build();
		var savedTheme = this.themeRepository.save(theme);
		return ThemeResponse.from(savedTheme);
	}

	public void delete(long id) {
		this.themeRepository.delete(id);
	}

	public Theme getThemeById(long id) {
		return this.themeRepository.findById(id);
	}

}
