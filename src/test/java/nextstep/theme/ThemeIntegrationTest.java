package nextstep.theme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import nextstep.theme.domain.Theme;
import nextstep.theme.persistence.ThemeDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ThemeIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ThemeDao themeDao;

    private MockMvc mockMvc;
    private Long initialDataId;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @BeforeEach
    void initializeData() {
        initialDataId = themeDao.insert(
            new Theme("자물쇠형", "장치나 소품없이 자물쇠만 배치된 테마", 22000L)
        );
    }

    @Test
    void makeTheme() throws Exception {
        String requestBody = objectMapper.writeValueAsString(
            new Theme("추리형", "방안에 단서를 뒤져 추리하는 테마", 32000L)
        );

        mockMvc.perform(
                post("/themes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", matchesRegex("/themes/\\d+")));

        assertThemeCount(2);
    }


    @Test
    void listThemes() throws Exception {
        mockMvc.perform(get("/themes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").isNumber())
            .andExpect(jsonPath("$[0].name").value("자물쇠형"))
            .andExpect(jsonPath("$[0].desc").value("장치나 소품없이 자물쇠만 배치된 테마"))
            .andExpect(jsonPath("$[0].price").value(22000L));
    }

    @Test
    void deleteTheme() throws Exception {
        mockMvc.perform(delete("/themes/{id}", initialDataId))
            .andExpect(status().isNoContent());

        assertThemeCount(0);
    }

    private void assertThemeCount(int count) {
        List<Theme> themes = themeDao.findAll();
        assertThat(themes).hasSize(count);
    }
}
