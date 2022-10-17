package nextstep.roomescape.theme.acceptance;

import nextstep.app.web.theme.port.web.ThemeCreateRequest;
import nextstep.app.web.theme.port.web.ThemeResponse;
import nextstep.roomescape.support.RoomEscapeAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;

class ThemeAcceptanceTest extends RoomEscapeAcceptanceTest {

    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final long PRICE = 22000L;

    @DisplayName("테마 생성")
    @Test
    void createTheme() throws Exception {
        ResultActions resultActions = doCreateTheme(NAME, DESC, PRICE);

        resultActions.andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/themes/" + extractIdFromLocation(resultActions))
        );
    }

    @DisplayName("테마 조회")
    @Test
    void findAllThemes() throws Exception {
        Long id = extractIdFromLocation(doCreateTheme(NAME, DESC, PRICE));

        doFindAllThemes()
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(List.of(new ThemeResponse(id, NAME, DESC, PRICE))))
                );
    }

    @DisplayName("테마 삭제")
    @Test
    void deleteTheme() throws Exception {
        Long id = extractIdFromLocation(doCreateTheme(NAME, DESC, PRICE));

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/themes/{id}", id)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNoContent()
                );

        doFindAllThemes()
                .andExpect(
                        MockMvcResultMatchers.content().string("[]")
                );
    }

    private ResultActions doCreateTheme(String name, String desc, Long price) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(new ThemeCreateRequest(name, desc, price)))
        );
    }

    private ResultActions doFindAllThemes() throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.get(UriComponentsBuilder.fromUriString("/themes")
                        .toUriString())
        );
    }
}
