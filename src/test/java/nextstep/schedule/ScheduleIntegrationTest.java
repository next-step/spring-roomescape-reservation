package nextstep.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.persistence.ScheduleDao;
import nextstep.schedule.web.request.MakeScheduleRequest;
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
public class ScheduleIntegrationTest {

    private static final String DATE_TEXT = "2022-08-11";
    private static final LocalDate DATE = LocalDate.parse(DATE_TEXT);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private ThemeDao themeDao;

    private MockMvc mockMvc;
    private Long initialScheduleId;
    private Long initialThemeId;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @BeforeEach
    void initializeData() {
        initialThemeId = themeDao.insert(
            new Theme("자물쇠형", "장치나 소품없이 자물쇠만 배치된 테마", 22000L)
        );
        initialScheduleId = scheduleDao.insert(
            new Schedule(initialThemeId, DATE, LocalTime.parse("12:00"))
        );
    }

    @Test
    void makeSchedule() throws Exception {
        String requestBody = objectMapper.writeValueAsString(
            new MakeScheduleRequest(initialThemeId, DATE_TEXT, "13:00")
        );

        mockMvc.perform(
                post("/schedules")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", matchesRegex("/schedules/\\d+")));

        assertScheduleCount(2);
    }

    @Test
    void listSchedules() throws Exception {
        mockMvc.perform(
                get("/schedules")
                    .param("themeId", "1")
                    .param("date", "2022-08-11")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").isNumber())
            .andExpect(jsonPath("$[0].theme.id").value(initialThemeId))
            .andExpect(jsonPath("$[0].theme.name").value("자물쇠형"))
            .andExpect(jsonPath("$[0].theme.desc").value("장치나 소품없이 자물쇠만 배치된 테마"))
            .andExpect(jsonPath("$[0].theme.price").value(22000L))
            .andExpect(jsonPath("$[0].date").value("2022-08-11"))
            .andExpect(jsonPath("$[0].time").value("12:00:00"));
    }

    @Test
    void listSchedulesWithNotFoundTheme() throws Exception {
        mockMvc.perform(
                get("/schedules")
                    .param("themeId", "999")
                    .param("date", "2022-08-11")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().string("아이디가 [999]인 테마를 찾을 수 없습니다."));
    }

    @Test
    void deleteSchedule() throws Exception {
        mockMvc.perform(delete("/schedules/{id}", initialScheduleId))
            .andExpect(status().isNoContent());

        assertScheduleCount(0);
    }

    private void assertScheduleCount(int count) {
        List<Schedule> schedules = scheduleDao.findBy(initialThemeId, DATE);
        assertThat(schedules).hasSize(count);
    }
}
