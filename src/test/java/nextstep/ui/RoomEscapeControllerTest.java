package nextstep.ui;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import nextstep.ApiDocument;
import nextstep.application.RoomEscapeService;
import nextstep.application.dto.ReservationReq;
import nextstep.application.dto.ReservationRes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(RoomEscapeController.class)
class RoomEscapeControllerTest extends ApiDocument {

  @MockBean
  private RoomEscapeService service;

  @Test
  void 예약_생성된다() throws Exception {
    //given
    var reservationReq = ReservationReq.builder()
        .date(LocalDate.now())
        .time("13:00")
        .name("gump")
        .build();
    given(service.create(reservationReq)).willReturn(1L);
    //when
    var actual = 예약_생성_요청(reservationReq);
    //then
    예약_생성_성공함(actual);
  }

  private ResultActions 예약_생성_요청(ReservationReq reservationReq) throws Exception {
    return mockMvc.perform(post("/reservations")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(reservationReq))
    );
  }

  private void 예약_생성_성공함(ResultActions actual) throws Exception {
    actual.andExpect(status().isCreated())
        .andDo(print())
        .andDo(toDocument("reservation-create"));
  }

  @Test
  void 예약_목록_조회된다() throws Exception {
    //given
    var date = LocalDate.now();
    var reservationRes = List.of(ReservationRes.builder()
        .id(1L)
        .date(date)
        .time("13:00")
        .name("gump")
        .build());
    given(service.findReservations(date)).willReturn(reservationRes);
    //when
    var actual = mockMvc.perform(get("/reservations?date={date}", date)
        .contentType(MediaType.APPLICATION_JSON)
    );
    //then
    actual.andExpect(status().isOk())
        .andDo(print())
        .andDo(toDocument("find-reservations"));

  }
}
