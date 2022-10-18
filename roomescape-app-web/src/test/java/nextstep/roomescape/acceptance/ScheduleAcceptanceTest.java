package nextstep.roomescape.acceptance;

import nextstep.app.web.schedule.port.web.ScheduleResponse;
import nextstep.app.web.theme.port.web.ThemeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

class ScheduleAcceptanceTest extends RoomEscapeAcceptanceTest {

    private static final String DATE = "2022-08-11";
    private static final String TIME = "13:00";
    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final long PRICE = 22000L;

    @DisplayName("스케줄 생성")
    @Test
    void createSchedule() throws Exception {
        Long themeId = extractIdFromLocation(doCreateTheme(NAME, DESC, PRICE));
        ResultActions resultActions = doCreateSchedule(themeId, DATE, TIME);

        resultActions.andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/schedules/" + extractIdFromLocation(resultActions))
        );
    }

    @DisplayName("스케줄 조회")
    @Test
    void findAllScheduleByThemeIdAndDate() throws Exception {
        Long themeId = extractIdFromLocation(doCreateTheme(NAME, DESC, PRICE));
        Long id = extractIdFromLocation(doCreateSchedule(themeId, DATE, TIME));

        doFindAllScheduleByThemeIdAndDate(themeId, DATE)
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(List.of(new ScheduleResponse(id, new ThemeResponse(themeId, NAME, DESC, PRICE), DATE, TIME))))
                );
    }

    @DisplayName("스케줄 삭제")
    @Test
    void deleteSchedule() throws Exception {
        Long themeId = extractIdFromLocation(doCreateTheme(NAME, DESC, PRICE));
        Long id = extractIdFromLocation(doCreateSchedule(themeId, DATE, TIME));

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/schedules/{id}", id)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNoContent()
                );

        doFindAllScheduleByThemeIdAndDate(themeId, DATE)
                .andExpect(
                        MockMvcResultMatchers.content().string("[]")
                );
    }

    private ResultActions doFindAllScheduleByThemeIdAndDate(Long themeId, String date) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.get(UriComponentsBuilder.fromUriString("/schedules")
                        .queryParam("themeId", themeId)
                        .queryParam("date", date)
                        .toUriString())
        );
    }
}
