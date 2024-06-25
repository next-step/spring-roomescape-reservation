package roomescape.presentation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.application.presentation.api.ThemeQueryApi;
import roomescape.application.presentation.api.dto.response.FindAllThemesResponse;
import roomescape.application.service.ThemeService;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.Themes;
import roomescape.domain.theme.vo.ThemeDescription;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.domain.theme.vo.ThemeThumbnail;
import roomescape.presentation.api.config.MockMvcCharacterEncodingConfig;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThemeQueryApi.class)
@Import({MockMvcCharacterEncodingConfig.class})
class ThemeQueryApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ThemeService themeService;

    @Autowired
    private MockMvc mockMvc;

    private Themes themes;

    @BeforeEach
    void setUp() {
        themes = new Themes(
                List.of(
                        new Theme(
                                new ThemeId(1L),
                                new ThemeName("레벨2 탈출"),
                                new ThemeDescription("우테코 레벨2를 탈출하는 내용입니다."),
                                new ThemeThumbnail("https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg")
                        )
                )
        );
    }

    @Test
    @DisplayName("방탈출 테마 전체 조회 API 컨트롤러 테스트")
    void findAllReservationsTest() throws Exception {
        given(themeService.findAll()).willReturn(themes);
        List<FindAllThemesResponse> response = FindAllThemesResponse.toFindAllThemesResponses(themes);

        mockMvc.perform(
                        get("/themes")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
}
