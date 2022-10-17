package nextstep.roomescape.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.RoomEscapeWebApplication;
import nextstep.domain.reservation.domain.model.ReservationRepository;
import nextstep.domain.theme.domain.model.ThemeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

@ActiveProfiles("local")
@SpringBootTest(classes = RoomEscapeWebApplication.class)
public class RoomEscapeAcceptanceTest {
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ThemeRepository themeRepository;

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
