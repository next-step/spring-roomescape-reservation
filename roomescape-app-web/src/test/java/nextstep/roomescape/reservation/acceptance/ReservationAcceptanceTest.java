package nextstep.roomescape.reservation.acceptance;

import nextstep.app.web.reservation.port.web.ReservationCreateRequest;
import nextstep.app.web.reservation.port.web.ReservationResponse;
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

class ReservationAcceptanceTest extends RoomEscapeAcceptanceTest {

    private static final String DATE = "2022-08-11";
    private static final String TIME = "13:00";
    private static final String NAME = "name";

    @DisplayName("예약 생성")
    @Test
    void createReservation() throws Exception {
        doCreateReservation()
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/reservations/1")
                );
    }

    @DisplayName("예약 조회")
    @Test
    void findAllReservations() throws Exception {
        Long id = extractIdFromLocation(doCreateReservation());

        mockMvc.perform(
                        MockMvcRequestBuilders.get(UriComponentsBuilder.fromUriString("/reservations")
                                .queryParam("date", "2022-08-11")
                                .toUriString())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(List.of(new ReservationResponse(id, DATE, TIME, NAME))))
                );
    }

    private ResultActions doCreateReservation() throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(new ReservationCreateRequest(DATE, TIME, NAME)))
        );
    }
}
