package roomescape.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.controller.dto.ThemeRequest;
import roomescape.controller.dto.ThemeResponse;
import roomescape.service.ThemeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ThemeControllerTests {

	@InjectMocks
	private ThemeController themeController;

	@Mock
	private ThemeService themeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getThemes() {
		// given
		List<ThemeResponse> themeResponses = new ArrayList<>();

		given(this.themeService.getThemes()).willReturn(themeResponses);

		// when
		ResponseEntity<List<ThemeResponse>> responseEntity = this.themeController.getThemes();

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(themeResponses);
	}

	@Test
	void create() {
		// given
		var themeRequest = new ThemeRequest("테마1", "첫번째테마", "테마이미지");
		var themeResponse = new ThemeResponse(1L, "테마1", "첫번째테마", "테마이미지");

		given(this.themeService.create(themeRequest)).willReturn(themeResponse);

		// when
		ResponseEntity<ThemeResponse> responseEntity = this.themeController.create(themeRequest);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(themeResponse);
	}

	@Test
	void delete() {
		// given
		long id = 1L;

		// when
		ResponseEntity<Void> responseEntity = this.themeController.delete(id);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		verify(this.themeService).delete(id);
	}

}
