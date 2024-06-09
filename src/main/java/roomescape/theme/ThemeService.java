package roomescape.theme;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
	private final ThemeRepository themeRepository;

	public ThemeService(ThemeRepository themeRepository) {
		this.themeRepository = themeRepository;
	}

	public List<ThemeResponse> findThemes() {
		return themeRepository.find().stream()
				.map(ThemeResponse::new)
				.collect(Collectors.toList());
	}

	public Long saveThemes(ThemeRequest request) {
		return themeRepository.save(request.getName(), request.getDescription(), request.getThumbnail());
	}

	public ThemeResponse findTheme(Long id) {
		return new ThemeResponse(themeRepository.findById(id));
	}

	public void deleteThemes(long id) {
		themeRepository.delete(id);
	}
}
