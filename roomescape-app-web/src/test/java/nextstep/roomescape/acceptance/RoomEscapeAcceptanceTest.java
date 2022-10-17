package nextstep.roomescape.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.RoomEscapeWebApplication;
import nextstep.app.web.theme.port.web.ThemeCreateRequest;
import nextstep.domain.reservation.domain.model.ReservationRepository;
import nextstep.domain.schedule.domain.model.ScheduleRepository;
import nextstep.domain.theme.domain.model.ThemeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@ActiveProfiles("local")
@SpringBootTest(classes = RoomEscapeWebApplication.class)
class RoomEscapeAcceptanceTest {
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        themeRepository.deleteAll();
        scheduleRepository.deleteAll();
    }

    protected ResultActions doCreateTheme(String name, String desc, Long price) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(new ThemeCreateRequest(name, desc, price)))
        );
    }

    protected Long extractIdFromLocation(ResultActions resultActions) {
        String location = Objects.requireNonNull(
                        resultActions.andReturn()
                                .getResponse()
                                .getHeaderValue(HttpHeaders.LOCATION))
                .toString();
        String[] split = location.split("/");

        return Long.parseLong(split[split.length - 1]);
    }
}
