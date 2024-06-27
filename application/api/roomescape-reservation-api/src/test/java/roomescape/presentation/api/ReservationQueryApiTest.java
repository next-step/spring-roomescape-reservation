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
import roomescape.application.presentation.api.ReservationQueryApi;
import roomescape.application.presentation.api.dto.response.FindAllReservationsResponse;
import roomescape.application.service.ReservationQueryService;
import roomescape.domain.reservation.ReservationView;
import roomescape.domain.reservation.ReservationViews;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.domain.theme.vo.ThemeName;
import roomescape.presentation.api.config.MockMvcCharacterEncodingConfig;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationQueryApi.class)
@Import({MockMvcCharacterEncodingConfig.class})
class ReservationQueryApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ReservationQueryService reservationQueryService;

    @Autowired
    private MockMvc mockMvc;

    private ReservationViews reservationViews;

    @BeforeEach
    void setUp() {
        ReservationView reservationView = new ReservationView(
                new ReservationId(1L),
                new ReservationName("kilian"),
                new ReservationDate(LocalDate.of(2024, 6, 6)),
                new ReservationTimeId(1L),
                new ReservationTimeStartAt(LocalTime.of(18, 24)),
                new ThemeId(1L),
                new ThemeName("레벨2 탈출")
        );

        reservationViews = new ReservationViews(List.of(reservationView));
    }

    @Test
    @DisplayName("예약 전체 조회 API 컨트롤러 테스트")
    void findAllReservationsTest() throws Exception {
        given(reservationQueryService.findAll()).willReturn(reservationViews);
        List<FindAllReservationsResponse> response =
                FindAllReservationsResponse.toFindAllReservationsResponses(reservationViews);

        mockMvc.perform(
                        get("/reservations")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
}
