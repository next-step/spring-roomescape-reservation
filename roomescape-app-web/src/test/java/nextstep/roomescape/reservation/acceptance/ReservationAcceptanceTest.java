package nextstep.roomescape.reservation.acceptance;

import nextstep.app.web.reservation.port.web.ReservationCreateRequest;
import nextstep.roomescape.support.RoomEscapeAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

class ReservationAcceptanceTest extends RoomEscapeAcceptanceTest {

    @DisplayName("예약 생성")
    @Test
    void createReservation() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(objectMapper.writeValueAsString(new ReservationCreateRequest("2022-08-11", "13:00", "name")))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.header().string("Location", "/reservations/1")
                );
    }
}
