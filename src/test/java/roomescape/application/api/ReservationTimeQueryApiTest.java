package roomescape.application.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.application.api.dto.response.FindAllReservationTimesResponse;
import roomescape.application.service.ReservationTimeService;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimes;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationTimeQueryApi.class)
class ReservationTimeQueryApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ReservationTimeService reservationTimeService;

    @Autowired
    private MockMvc mockMvc;

    private ReservationTimes reservationTimes;

    @BeforeEach
    void setUp() {
        ReservationTime reservationTime = new ReservationTime(
                new ReservationTimeId(1L),
                new ReservationTimeStartAt(LocalTime.of(16, 1))
        );

        reservationTimes = new ReservationTimes(List.of(reservationTime));
    }

    @Test
    @DisplayName("예약시간 전체 조회 API 컨트롤러 테스트")
    void findAllReservationsTest() throws Exception {
        given(reservationTimeService.findAllReservationTimes()).willReturn(reservationTimes);
        List<FindAllReservationTimesResponse> response = FindAllReservationTimesResponse.toFindAllReservationResponses(reservationTimes);

        mockMvc.perform(
                        get("/times")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
}
