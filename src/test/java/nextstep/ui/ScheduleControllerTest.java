package nextstep.ui;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.ApiDocument;
import nextstep.application.schedule.ScheduleService;
import nextstep.application.schedule.dto.Schedule;
import nextstep.application.schedule.dto.ScheduleRes;
import nextstep.application.themes.dto.ThemesRes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest extends ApiDocument {

  @MockBean
  private ScheduleService service;

  @Test
  void 스케쥴_생성한다() throws Exception {
    //given
    var schedule = Schedule.builder()
        .themeId(1L)
        .date(LocalDate.now())
        .time("13:00")
        .build();
    given(service.create(schedule)).willReturn(1L);
    //when
    var actual = mockMvc.perform(post("/schedules")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(schedule))
    );
    //then
    actual.andExpect(status().isCreated())
        .andDo(print())
        .andDo(toDocument("create-schedule"));
  }

  @Test
  void 스케쥴_목록을_조회한다() throws Exception {
    //given
    var themeId = 1L;
    var date = LocalDate.now();
    var schedule = List.of(
        ScheduleRes.builder()
            .id(1L)
            .theme(
                ThemesRes.builder()
                    .id(1L)
                    .name("테마이름")
                    .desc("테마설명")
                    .price(BigDecimal.valueOf(22000))
                    .build()
            )
            .date(date)
            .time(LocalTime.now())
            .build()
    );
    given(service.getSchedule(themeId, date)).willReturn(schedule);
    //when
    var actual = mockMvc.perform(get("/schedules?themeId={themeId}&date={date}", themeId, date)
        .contentType(MediaType.APPLICATION_JSON)
    );
    //then
    actual.andExpect(status().isOk())
        .andDo(print())
        .andDo(toDocument("get-schedule"));
  }

  @Test
  void 스케쥴_삭제한다() throws Exception {
    //given
    var id = 1L;
    willDoNothing().given(service).deleteSchedule(id);
    //when
    var actual = mockMvc.perform(delete("/schedules/{id}", id));
    //then
    actual.andExpect(status().isNoContent())
        .andDo(print())
        .andDo(toDocument("delete-schedule"));
  }
}
