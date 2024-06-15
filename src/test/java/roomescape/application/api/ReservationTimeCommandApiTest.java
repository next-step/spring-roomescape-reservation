package roomescape.application.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.application.api.dto.request.CreateReservationTimeRequest;
import roomescape.application.api.dto.response.CreateReservationTimeResponse;
import roomescape.application.service.ReservationTimeService;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationTimeCommandApi.class)
class ReservationTimeCommandApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockBean
    private ReservationTimeService reservationTimeService;

    @Autowired
    private MockMvc mockMvc;

    private ReservationTime reservationTime;

    @BeforeEach
    void setUp() {
        reservationTime = new ReservationTime(
                new ReservationTimeId(1L),
                new ReservationTimeStartAt(LocalTime.of(20, 21))
        );
    }

    @Test
    @DisplayName("예약 시간 생성 API 컨트롤러 테스트")
    void createReservationTimeTest() throws Exception {
        CreateReservationTimeRequest request = new CreateReservationTimeRequest("20:21");

        given(reservationTimeService.createReservationTime(any())).willReturn(reservationTime);

        CreateReservationTimeResponse response = CreateReservationTimeResponse.from(reservationTime);

        mockMvc.perform(
                        post("/times")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("예약 시간 삭제 API 컨트롤러 테스트")
    void deleteReservationTimeTest() throws Exception {
        Long reservationTimeId = 1L;

        doNothing().when(reservationTimeService).deleteReservationTime(any());

        mockMvc.perform(
                delete("/times/{reservationTimeId}", reservationTimeId)
        ).andExpect(status().isOk());
    }
}
