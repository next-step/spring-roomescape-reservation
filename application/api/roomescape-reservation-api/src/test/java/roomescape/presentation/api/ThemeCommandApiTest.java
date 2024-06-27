package roomescape.presentation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.application.presentation.api.ThemeCommandApi;
import roomescape.application.presentation.api.dto.request.CreateThemeRequest;
import roomescape.application.presentation.api.dto.response.CreateThemeResponse;
import roomescape.application.service.ThemeService;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.vo.ThemeDescription;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.domain.theme.vo.ThemeThumbnail;
import roomescape.presentation.api.config.MockMvcCharacterEncodingConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThemeCommandApi.class)
@Import({MockMvcCharacterEncodingConfig.class})
class ThemeCommandApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ThemeService themeService;

    @Autowired
    private MockMvc mockMvc;

    private Theme theme;

    @BeforeEach
    void setUp() {
        theme = new Theme(
                new ThemeId(1L),
                new ThemeName("레벨2 탈출"),
                new ThemeDescription("우테코 레벨2를 탈출하는 내용입니다."),
                new ThemeThumbnail("https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg")
        );
    }

    @Test
    @DisplayName("방탈출 테마 생성 API 컨트롤러 테스트")
    void createThemeTest() throws Exception {
        CreateThemeRequest request = new CreateThemeRequest(
                "레벨2 탈출",
                "우테코 레벨2를 탈출하는 내용입니다.",
                "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"
        );

        given(themeService.create(any())).willReturn(theme);

        CreateThemeResponse response = CreateThemeResponse.from(theme);

        mockMvc.perform(
                        post("/themes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("방탈출 테마 삭제 API 컨트롤러 테스트")
    void deleteReservationTimeTest() throws Exception {
        Long themeId = 1L;

        doNothing().when(themeService).delete(any());

        mockMvc.perform(
                delete("/themes/{themeId}", themeId)
        ).andExpect(status().isNoContent());
    }
}
