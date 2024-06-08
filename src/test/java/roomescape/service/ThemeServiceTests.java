package roomescape.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.controller.dto.ThemeRequest;
import roomescape.domain.Theme;
import roomescape.repository.ThemeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ThemeServiceTests {

	@InjectMocks
	private ThemeService themeService;

	@Mock
	private ThemeRepository themeRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getThemes() {
		// given
		List<Theme> themes = new ArrayList<>();

		Theme theme = Theme.builder().id(1L).name("테마1").description("첫번째테마").thumbnail("썸네일이미지").build();

		themes.add(theme);

		given(this.themeRepository.findAll()).willReturn(themes);

		// when
		var resultThemes = this.themeService.getThemes();

		// then
		assertThat(resultThemes).isNotEmpty();
		assertThat(resultThemes).hasSize(1);
		assertThat(resultThemes).allSatisfy((themeResponse) -> {
			assertThat(themeResponse.id()).isEqualTo(1L);
			assertThat(themeResponse.name()).isEqualTo("테마1");
			assertThat(themeResponse.description()).isEqualTo("첫번째테마");
			assertThat(themeResponse.thumbnail()).isEqualTo("썸네일이미지");
		});
	}

	@Test
	void create() {
		// given
		Theme theme = Theme.builder().name("테마1").description("첫번째테마").thumbnail("썸네일이미지").build();

		given(this.themeRepository.save(theme)).willAnswer((invocationOnMock) -> {
			Theme savedTheme = invocationOnMock.getArgument(0);
			savedTheme.setId(1L);
			return savedTheme;
		});

		ThemeRequest request = new ThemeRequest("테마1", "첫번째테마", "썸네일이미지");

		// when
		var createdTheme = this.themeService.create(request);

		// then
		assertThat(createdTheme).isNotNull();
		assertThat(createdTheme.id()).isEqualTo(1L);
		assertThat(createdTheme.name()).isEqualTo("테마1");
		assertThat(createdTheme.description()).isEqualTo("첫번째테마");
		assertThat(createdTheme.thumbnail()).isEqualTo("썸네일이미지");
	}

	@Test
	void delete() {
		// given
		long id = 1L;

		// when
		this.themeService.delete(id);

		// then
		verify(this.themeRepository, times(1)).delete(id);
	}

	@Test
	void getThemeById() {
		// given
		Theme theme = Theme.builder().id(1L).name("테마1").description("첫번째테마").thumbnail("썸네일이미지").build();

		given(this.themeRepository.findById(anyLong())).willReturn(theme);

		// when
		var resultThemeById = this.themeService.getThemeById(1L);

		// then
		assertThat(resultThemeById).isNotNull();
		assertThat(resultThemeById.getId()).isEqualTo(1L);
		assertThat(resultThemeById.getName()).isEqualTo("테마1");
		assertThat(resultThemeById.getDescription()).isEqualTo("첫번째테마");
		assertThat(resultThemeById.getThumbnail()).isEqualTo("썸네일이미지");
	}

}
