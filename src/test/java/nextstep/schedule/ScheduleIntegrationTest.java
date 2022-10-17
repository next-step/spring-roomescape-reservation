package nextstep.schedule;

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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.persistence.ScheduleDao;
import nextstep.schedule.web.request.MakeScheduleRequest;
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

    private static final Long THEME_ID = 1L;
    private static final String DATE_TEXT = "2022-08-11";
    private static final LocalDate DATE = LocalDate.parse(DATE_TEXT);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduleDao scheduleDao;

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
        initialDataId = scheduleDao.insert(
            new Schedule(THEME_ID, DATE, LocalTime.parse("12:00"))
        );
    }

    @Test
    void makeSchedule() throws Exception {
        String requestBody = objectMapper.writeValueAsString(
            new MakeScheduleRequest(THEME_ID, DATE_TEXT, "13:00")
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
            .andExpect(jsonPath("$[0].themeId").value(1L))
            .andExpect(jsonPath("$[0].date").value("2022-08-11"))
            .andExpect(jsonPath("$[0].time").value("12:00:00"));
    }

    @Test
    void deleteSchedule() throws Exception {
        mockMvc.perform(delete("/schedules/{id}", initialDataId))
            .andExpect(status().isNoContent());

        assertScheduleCount(0);
    }

    private void assertScheduleCount(int count) {
        List<Schedule> schedules = scheduleDao.findBy(THEME_ID, DATE);
        assertThat(schedules).hasSize(count);
    }
}
