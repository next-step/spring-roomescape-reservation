package nextstep.roomescape.acceptance;

import nextstep.app.web.reservation.port.web.ReservationCreateRequest;
import nextstep.app.web.reservation.port.web.ReservationResponse;
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
        Long themeId = extractIdFromLocation(doCreateTheme("name", "desc", 22000L));
        Long scheduleId = extractIdFromLocation(doCreateSchedule(themeId, DATE, TIME));
        ResultActions resultActions = doCreateReservation(scheduleId, DATE, TIME, NAME);

        resultActions.andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/reservations/" + extractIdFromLocation(resultActions))
        );
    }

    @DisplayName("예약 조회")
    @Test
    void findAllReservations() throws Exception {
        Long themeId = extractIdFromLocation(doCreateTheme("name", "desc", 22000L));
        Long scheduleId = extractIdFromLocation(doCreateSchedule(themeId, DATE, TIME));
        Long id = extractIdFromLocation(doCreateReservation(scheduleId, DATE, TIME, NAME));

        doFindAllReservations()
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(List.of(new ReservationResponse(id, DATE, TIME, NAME))))
                );
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() throws Exception {
        Long themeId = extractIdFromLocation(doCreateTheme("name", "desc", 22000L));
        Long scheduleId = extractIdFromLocation(doCreateSchedule(themeId, DATE, TIME));
        doCreateReservation(scheduleId, DATE, TIME, NAME);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(UriComponentsBuilder.fromUriString("/reservations")
                                .queryParam("scheduleId", scheduleId)
                                .queryParam("date", DATE)
                                .queryParam("time", TIME)
                                .toUriString())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNoContent()
                );

        doFindAllReservations()
                .andExpect(
                        MockMvcResultMatchers.content().string("[]")
                );
    }

    private ResultActions doCreateReservation(Long scheduleId, String date, String time, String name) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(new ReservationCreateRequest(scheduleId, date, time, name)))
        );
    }

    private ResultActions doFindAllReservations() throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.get(UriComponentsBuilder.fromUriString("/reservations")
                        .queryParam("date", DATE)
                        .toUriString())
        );
    }
}
